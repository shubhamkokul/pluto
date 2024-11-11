package castiel.solutionbyhour.model.user.createuser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateUserResponse.class)
public interface CreateUserResponse {
    String username();
    String authToken();
    String email();
    Long customerId();
}
