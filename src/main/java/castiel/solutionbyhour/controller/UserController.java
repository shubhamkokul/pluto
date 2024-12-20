package castiel.solutionbyhour.controller;

import castiel.solutionbyhour.model.user.createuser.CreateUserRequest;
import castiel.solutionbyhour.model.user.validateuser.ValidateUserRequest;
import castiel.solutionbyhour.processor.user.CreateUser;
import castiel.solutionbyhour.processor.user.ValidateUser;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UserController implements PlutoEndpoint {

    private final CreateUser createUser;
    private final ValidateUser validateUser;

    @Inject
    public UserController(CreateUser createUser, ValidateUser validateUser) {
        this.createUser = createUser;
        this.validateUser = validateUser;
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

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> validateUser(@QueryParam("username") String username,
                                      @QueryParam("email") String email) {
        return Uni.createFrom()
                .item(() -> validateUser.process(username, email))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(result -> Response.ok(result).build());
    }
}
