package castiel.solutionbyhour.model.user.createuser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateUserRequest.class)
public interface CreateUserRequest {
    String name();
    String username();
    String password();
    String email();
}
