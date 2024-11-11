package castiel.solutionbyhour.processor.user;

import castiel.solutionbyhour.model.data.AuthenticationEntity;
import castiel.solutionbyhour.model.data.UserEntity;
import castiel.solutionbyhour.model.user.PasswordHashContext;
import castiel.solutionbyhour.model.user.createuser.CreateUserRequest;
import castiel.solutionbyhour.model.user.createuser.CreateUserResponse;
import castiel.solutionbyhour.model.user.createuser.ImmutableCreateUserResponse;
import castiel.solutionbyhour.model.web.BaseResponse;
import castiel.solutionbyhour.model.web.ImmutableBaseResponse;
import castiel.solutionbyhour.persistence.AuthenticationRepository;
import castiel.solutionbyhour.persistence.UserRepository;
import castiel.solutionbyhour.processor.user.service.PasswordHasher;
import castiel.solutionbyhour.processor.user.service.TokenService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

    public BaseResponse<CreateUserResponse> process(CreateUserRequest createUserRequest) {
        // Use CompletableFuture to handle async operations
        CompletableFuture<UserEntity> userEntityFuture = CompletableFuture.supplyAsync(() -> userRepository.createUser(buildUserEntity(createUserRequest)));

        CompletableFuture<Optional<PasswordHashContext>> passwordHashContextFuture = CompletableFuture.supplyAsync(() -> passwordHasher.hashPassword(createUserRequest.password()));

        CompletableFuture<String> authTokenFuture = CompletableFuture.supplyAsync(() -> tokenService.createAuthToken(createUserRequest.username()));

        // Combine all futures to process once all tasks complete
        return CompletableFuture.allOf(userEntityFuture, passwordHashContextFuture, authTokenFuture).thenApplyAsync(voidResult -> {
            try {
                // Collect results from all futures
                UserEntity userEntity = userEntityFuture.get();
                Optional<PasswordHashContext> passwordHashContextOpt = passwordHashContextFuture.get();
                String authToken = authTokenFuture.get();

                if (passwordHashContextOpt.isPresent()) {
                    AuthenticationEntity authenticationEntity = insertIntoAuthentication(userEntity.customerId, passwordHashContextOpt.get(), authToken);

                    // Return response
                    return ImmutableBaseResponse.<CreateUserResponse>builder().response(ImmutableCreateUserResponse.builder()
                            .authToken(authenticationEntity.jwtToken)
                            .customerId(userEntity.customerId)
                            .email(userEntity.email)
                            .username(userEntity.username)
                            .build())
                            .message("Created a new user").build();
                } else {
                    // Handle the case where password hash context is not present
                    return ImmutableBaseResponse.<CreateUserResponse>builder().message("Failed to hash password").build();
                }
            } catch (Exception e) {
                // Handle exceptions like UserAlreadyExistsException
                return ImmutableBaseResponse.<CreateUserResponse>builder().message("An error occurred during user creation: " + e.getMessage()).build();
            }
        }).join(); // wait for completion and return the final response
    }

    private AuthenticationEntity insertIntoAuthentication(Long customerId, PasswordHashContext passwordHashContext, String authToken) {
        return authenticationRepository.createAuthentication(
                buildAuthenticationEntity(customerId, passwordHashContext, authToken));
    }


    private UserEntity buildUserEntity(CreateUserRequest createUserRequest) {
        if (createUserRequest == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.name = createUserRequest.name();
        userEntity.email = createUserRequest.email();
        userEntity.username = createUserRequest.username();
        return userEntity;
    }

    private AuthenticationEntity buildAuthenticationEntity(Long customerId, PasswordHashContext passwordHashContext, String authToken) {
        AuthenticationEntity authenticationEntity = new AuthenticationEntity();
        authenticationEntity.customerId = customerId;
        authenticationEntity.jwtToken = authToken;
        authenticationEntity.passwordHash = passwordHashContext.hashedPassword();
        authenticationEntity.salt = passwordHashContext.generatedSalt();
        return authenticationEntity;
    }
}
