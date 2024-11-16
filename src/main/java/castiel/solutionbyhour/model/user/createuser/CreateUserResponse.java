package castiel.solutionbyhour.model.user.createuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateUserResponse.class)
public interface CreateUserResponse {
    @JsonProperty("username")
    String username();
    @JsonProperty("auth_token")
    String authToken();
    @JsonProperty("email")
    String email();
    @JsonProperty("customer_id")
    Long customerId();
}
