package castiel.solutionbyhour.model.user.validateuser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableValidateUserResponse.class)
public interface ValidateUserResponse {
    
    Boolean existingUser();
}
