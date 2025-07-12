package castiel.solutionbyhour.processor.user;

import castiel.solutionbyhour.model.data.AuthenticationEntity;
import castiel.solutionbyhour.model.data.UserEntity;
import castiel.solutionbyhour.model.auth.PasswordHashContext;
import castiel.solutionbyhour.model.user.createuser.CreateUserRequest;
import castiel.solutionbyhour.model.user.createuser.CreateUserResponse;
import castiel.solutionbyhour.model.user.createuser.ImmutableCreateUserResponse;
import castiel.solutionbyhour.model.web.BaseResponse;
import castiel.solutionbyhour.model.web.ImmutableBaseResponse;
import castiel.solutionbyhour.persistence.AuthenticationRepository;
import castiel.solutionbyhour.persistence.UserRepository;
import castiel.solutionbyhour.core.auth.PasswordHasher;
import castiel.solutionbyhour.core.auth.TokenService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@ApplicationScoped
public class CreateUser {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final TokenService tokenService;
    private final PasswordHasher passwordHasher;

    @Inject
    public CreateUser(UserRepository userRepository, AuthenticationRepository authenticationRepository, TokenService tokenService, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.tokenService = tokenService;
        this.passwordHasher = passwordHasher;
    }

    @Transactional
    public BaseResponse<CreateUserResponse> process(CreateUserRequest createUserRequest) {
        // Validate the input
        Optional<BaseResponse<CreateUserResponse>> validationResponse = validateCreateUserRequest(createUserRequest);
        if (validationResponse.isPresent()) {
            return validationResponse.get();
        }

        // Process password hashing and token generation asynchronously
        CompletableFuture<PasswordHashContext> passwordHashContextFuture = CompletableFuture.supplyAsync(() -> passwordHasher.hashPassword(createUserRequest.password()));

        CompletableFuture<String> authTokenFuture = CompletableFuture.supplyAsync(() -> tokenService.createAuthToken(createUserRequest.username()));

        // Wait for both asynchronous tasks to complete and process the results
        return CompletableFuture.allOf(passwordHashContextFuture, authTokenFuture).thenApplyAsync(voidResult -> {
            try {
                // Await completion of both tasks and collect results
                PasswordHashContext passwordHashContext = passwordHashContextFuture.get();
                String authToken = authTokenFuture.get();

                // Process result and create user
                // Create user and authentication
                UserEntity userEntity = userRepository.createUser(buildUserEntity(createUserRequest));
                authenticationRepository.createAuthentication(buildAuthenticationEntity(userEntity.customerId, passwordHashContext));

                // Return response
                return ImmutableBaseResponse.<CreateUserResponse>builder().data(ImmutableCreateUserResponse.builder().authToken(authToken).customerId(userEntity.customerId).email(userEntity.email).username(userEntity.username).build()).message("Created a new user.").build();
            } catch (Exception e) {
                // Catch exceptions and return an error message
                return ImmutableBaseResponse.<CreateUserResponse>builder().message("An error occurred during user creation: " + e.getMessage()).build();
            }
        }).join(); // Wait for the completion of all futures before returning the response
    }

    private Optional<BaseResponse<CreateUserResponse>> validateCreateUserRequest(CreateUserRequest createUserRequest) {
        if (createUserRequest == null) {
            return Optional.of(buildErrorResponse("Invalid input: CreateUserRequest cannot be null."));
        }

        if (isNullOrEmpty(createUserRequest.password())) {
            return Optional.of(buildErrorResponse("Invalid input: Password cannot be empty."));
        }

        if (!isValidEmail(createUserRequest.email())) {
            return Optional.of(buildErrorResponse("Invalid email format."));
        }

        return Optional.empty();
    }

    private BaseResponse<CreateUserResponse> buildErrorResponse(String message) {
        return ImmutableBaseResponse.<CreateUserResponse>builder().message(message).build();
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Regex to match a broad spectrum of valid email formats
        String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)$";
        return Pattern.matches(emailRegex, email);
    }

    private UserEntity buildUserEntity(CreateUserRequest createUserRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.name = createUserRequest.name();
        userEntity.email = createUserRequest.email();
        userEntity.username = createUserRequest.username();
        return userEntity;
    }

    private AuthenticationEntity buildAuthenticationEntity(Long customerId, PasswordHashContext passwordHashContext) {
        AuthenticationEntity authenticationEntity = new AuthenticationEntity();
        authenticationEntity.customerId = customerId;
        authenticationEntity.passwordHash = passwordHashContext.hashedPassword();
        authenticationEntity.salt = passwordHashContext.generatedSalt();
        return authenticationEntity;
    }
}