package castiel.solutionbyhour.model.user.validateuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableValidateUserResponse.class)
public interface ValidateUserResponse {

    @JsonProperty("existing_user")
    Boolean existingUser();
}
