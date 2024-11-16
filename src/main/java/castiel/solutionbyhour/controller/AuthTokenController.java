package castiel.solutionbyhour.controller;

import castiel.solutionbyhour.model.auth.createtoken.CreateAuthTokenRequest;
import castiel.solutionbyhour.processor.authtoken.CreateAuthToken;
import castiel.solutionbyhour.processor.authtoken.ValidateAuthToken;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthTokenController implements PlutoEndpoint {

    private final CreateAuthToken createAuthToken;
    private final ValidateAuthToken validateAuthToken;

    public AuthTokenController(CreateAuthToken createAuthToken, ValidateAuthToken validateAuthToken) {
        this.createAuthToken = createAuthToken;
        this.validateAuthToken = validateAuthToken;
    }

    @POST
    @Path("/auth/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createAuthToken(CreateAuthTokenRequest createAuthTokenRequest) {
        return Uni.createFrom().item(() -> createAuthToken.process(createAuthTokenRequest))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool()) // Use the worker thread pool
                .map(result -> Response.ok().entity(result).build());
    }

    @GET
    @Path("/auth/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> validateAuthToken(@QueryParam("auth_token") String authToken) {
        return Uni.createFrom().item(() -> validateAuthToken.process(authToken))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool()) // Use the worker thread pool
                .map(result -> Response.ok().entity(result).build());
    }
}
