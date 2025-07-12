package castiel.solutionbyhour.processor.authtoken;

import castiel.solutionbyhour.core.auth.PasswordHasher;
import castiel.solutionbyhour.core.auth.TokenService;
import castiel.solutionbyhour.model.auth.PasswordHashContext;
import castiel.solutionbyhour.model.auth.createtoken.CreateAuthTokenRequest;
import castiel.solutionbyhour.model.auth.createtoken.CreateAuthTokenResponse;
import castiel.solutionbyhour.model.auth.createtoken.ImmutableCreateAuthTokenResponse;
import castiel.solutionbyhour.model.data.AuthenticationEntity;
import castiel.solutionbyhour.model.data.UserEntity;
import castiel.solutionbyhour.model.web.BaseResponse;
import castiel.solutionbyhour.model.web.ImmutableBaseResponse;
import castiel.solutionbyhour.persistence.AuthenticationRepository;
import castiel.solutionbyhour.persistence.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@ApplicationScoped
public class CreateAuthToken {

    private static final String PASSWORD_MANDATORY_MSG = "Password is mandatory";
    private static final String USER_NOT_FOUND_MSG = "Username or Email not found";
    private static final String CREDENTIALS_MISMATCH_MSG = "Credentials did not match";
    private static final String TOKEN_CREATION_ERROR_MSG = "An error occurred during auth token creation: ";

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final PasswordHasher passwordHasher;
    private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(CreateAuthToken.class);

    @Inject
    public CreateAuthToken(UserRepository userRepository, AuthenticationRepository authenticationRepository,
                           PasswordHasher passwordHasher, TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.passwordHasher = passwordHasher;
        this.tokenService = tokenService;
    }

    public BaseResponse<CreateAuthTokenResponse> process(CreateAuthTokenRequest createAuthTokenRequest) {
        // Validate request
        Optional<String> validationError = validateRequest(createAuthTokenRequest);
        if (validationError.isPresent()) {
            return buildErrorResponse(validationError.get());
        }

        try {
            // Fetch user entity
            Optional<UserEntity> userEntityOpt = Optional.ofNullable(
                    userRepository.findByUsernameOrEmail(createAuthTokenRequest.username(), createAuthTokenRequest.email()));

            if (userEntityOpt.isEmpty()) {
                return buildErrorResponse(USER_NOT_FOUND_MSG);
            }

            UserEntity userEntity = userEntityOpt.get();
            AuthenticationEntity authenticationEntity = authenticationRepository.findByCustomerId(userEntity.customerId);

            // Validate password
            if (!isPasswordValid(createAuthTokenRequest.password(), authenticationEntity)) {
                return buildErrorResponse(CREDENTIALS_MISMATCH_MSG);
            }

            // Generate auth token
            String authToken = tokenService.createAuthToken(userEntity.username);
            return buildSuccessResponse(authToken);

        } catch (Exception e) {
            logger.error(TOKEN_CREATION_ERROR_MSG + e.getMessage(), e);
            return buildErrorResponse(TOKEN_CREATION_ERROR_MSG + e.getMessage());
        }
    }

    // Helper method to validate request
    private Optional<String> validateRequest(CreateAuthTokenRequest createAuthTokenRequest) {
        if (createAuthTokenRequest.password() == null || createAuthTokenRequest.password().isEmpty()) {
            return Optional.of(PASSWORD_MANDATORY_MSG);
        }
        return Optional.empty();
    }

    // Helper method to validate password
    private boolean isPasswordValid(String password, AuthenticationEntity authenticationEntity) {
        PasswordHashContext passwordHashContext = passwordHasher.hashPassword(password, authenticationEntity.salt);
        return authenticationEntity.passwordHash.contentEquals(passwordHashContext.hashedPassword());
    }

    // Helper method to build success response
    private BaseResponse<CreateAuthTokenResponse> buildSuccessResponse(String authToken) {
        CreateAuthTokenResponse response = ImmutableCreateAuthTokenResponse.builder()
                .authToken(authToken)
                .build();
        return ImmutableBaseResponse.<CreateAuthTokenResponse>builder()
                .data(response)
                .message("Auth Token Created")
                .build();
    }

    // Helper method to build error response
    private BaseResponse<CreateAuthTokenResponse> buildErrorResponse(String message) {
        return ImmutableBaseResponse.<CreateAuthTokenResponse>builder()
                .message(message)
                .build();
    }
}