package castiel.solutionbyhour.controller;

import castiel.solutionbyhour.model.incoming.authentication.Credential;
import castiel.solutionbyhour.processor.authentication.AuthenticationProcessor;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthenticationController implements PlutoEndpoint {

    private final AuthenticationProcessor authenticationProcessor;

    @Inject
    public AuthenticationController(AuthenticationProcessor authenticationProcessor) {
        this.authenticationProcessor = authenticationProcessor;
    }

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createUser(Credential credential) {
        return Uni.createFrom().item(
                Response.ok()
                        .entity(authenticationProcessor.apply(credential))
                        .build()
        );
    }
}
