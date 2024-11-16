package castiel.solutionbyhour.model.user.validateuser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableValidateUserRequest.class)
public interface ValidateUserRequest {
    @Nullable
    String username();
    @Nullable
    String email();
}
