package castiel.solutionbyhour.processor.authtoken;

import castiel.solutionbyhour.core.auth.TokenService;
import castiel.solutionbyhour.model.auth.TokenContext;
import castiel.solutionbyhour.model.auth.validatetoken.ImmutableValidateAuthTokenResponse;
import castiel.solutionbyhour.model.auth.validatetoken.ValidateAuthTokenResponse;
import castiel.solutionbyhour.model.web.BaseResponse;
import castiel.solutionbyhour.model.web.ImmutableBaseResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ValidateAuthToken {

    private static final String INVALID_TOKEN_MESSAGE = "Invalid token";

    private final TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(ValidateAuthToken.class);

    @Inject
    public ValidateAuthToken(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public BaseResponse<ValidateAuthTokenResponse> process(String token) {
        try {
            // Validate the token
            TokenContext tokenContext = tokenService.validateToken(token);

            // Check if the token is valid and return appropriate response
            if (tokenContext.result()) {
                return buildSuccessResponse(tokenContext);
            } else {
                return buildErrorResponse(INVALID_TOKEN_MESSAGE);
            }

        } catch (Exception e) {
            // Log the exception and return an error response
            logger.error("Error while validating token: {}", e.getMessage(), e);
            return buildErrorResponse("An error occurred during token validation");
        }
    }

    // Helper method to build success response
    private BaseResponse<ValidateAuthTokenResponse> buildSuccessResponse(TokenContext tokenContext) {
        ValidateAuthTokenResponse response = ImmutableValidateAuthTokenResponse.builder()
                .valid(tokenContext.result())
                .build();
        return ImmutableBaseResponse.<ValidateAuthTokenResponse>builder()
                .response(response)
                .build();
    }

    // Helper method to build error response
    private BaseResponse<ValidateAuthTokenResponse> buildErrorResponse(String message) {
        return ImmutableBaseResponse.<ValidateAuthTokenResponse>builder()
                .message(message)
                .build();
    }
}