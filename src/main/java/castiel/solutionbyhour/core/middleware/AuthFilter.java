package castiel.solutionbyhour.core.middleware;

import java.io.IOException;

import org.jboss.logging.Logger;

import castiel.solutionbyhour.core.auth.AuthRequired;
import castiel.solutionbyhour.core.auth.TokenService;
import castiel.solutionbyhour.model.auth.TokenContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@AuthRequired
public class AuthFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(AuthFilter.class);

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String MISSING_AUTH_HEADER_MESSAGE = "Authorization header is missing or invalid";
    private static final String TOKEN_INVALID_MESSAGE = "Invalid token";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenService tokenService;

    @Inject
    public AuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(AUTHORIZATION_HEADER);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            abortRequest(requestContext, MISSING_AUTH_HEADER_MESSAGE);
            return;
        }

        // Extract the token after "Bearer "
        String token = authHeader.substring(BEARER_PREFIX.length()).trim();

        TokenContext tokenContext;
        try {
            tokenContext = tokenService.validateToken(token);
        } catch (Exception e) {
            LOGGER.error("Token validation failed", e);
            abortRequest(requestContext, TOKEN_INVALID_MESSAGE);
            return;
        }

        if (tokenContext.result()) {
            String username = tokenContext.email().orElse("Unknown");
            LOGGER.infof("Authorized request by user: %s", username);
        } else {
            abortRequest(requestContext, TOKEN_INVALID_MESSAGE);
        }
    }

    private void abortRequest(ContainerRequestContext requestContext, String message) {
        LOGGER.warnf("Request aborted: %s", message);
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity(message)
                        .build()
        );
    }
}