package castiel.solutionbyhour.model.user.createuser;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateUserResponse.class)
public interface CreateUserResponse {
    @JsonProperty("auth_token")
    String authToken();
    @JsonProperty("email")
    String email();
    @JsonProperty("customer_id")
    Long customerId();
}
