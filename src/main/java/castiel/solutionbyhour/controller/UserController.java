package castiel.solutionbyhour.controller;

import castiel.solutionbyhour.model.user.createuser.CreateUserRequest;
import castiel.solutionbyhour.processor.user.CreateUser;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UserController implements PlutoEndpoint {

    private final CreateUser createUser;

    @Inject
    public UserController(CreateUser createUser) {
        this.createUser = createUser;
    }

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createUser(CreateUserRequest createUserRequest) {
        return Uni.createFrom().item(() -> createUser.process(createUserRequest))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool()) // Use the worker thread pool
                .map(result -> Response.ok().entity(result).build());
    }
}
