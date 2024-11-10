package castiel.soutionbyhour.controller;

import castiel.soutionbyhour.model.incoming.authentication.Credential;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthenticationController implements PlutoEndpoint {

    @POST
    @Path("/auth/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createAuthToken(Credential credential) {
        return Uni.createFrom().item(
                Response.ok()
                        .entity("Token will be here")
                        .build()
        );
    }
}
