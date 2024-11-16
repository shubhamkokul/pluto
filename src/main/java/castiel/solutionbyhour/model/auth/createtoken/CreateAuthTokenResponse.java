package castiel.solutionbyhour.model.auth.createtoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateAuthTokenResponse.class)
public interface CreateAuthTokenResponse {
    @JsonProperty("auth_token")
    String authToken();
}
