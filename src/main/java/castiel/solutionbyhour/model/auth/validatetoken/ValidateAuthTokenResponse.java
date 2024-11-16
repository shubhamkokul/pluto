package castiel.solutionbyhour.model.auth.validatetoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableValidateAuthTokenResponse.class)
public interface ValidateAuthTokenResponse {
    @JsonProperty("valid")
    Boolean valid();
}
