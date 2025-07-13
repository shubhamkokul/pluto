package castiel.solutionbyhour.model.user.validateuser;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.annotation.Nullable;

@Value.Immutable
@JsonDeserialize(as = ImmutableValidateUserRequest.class)
public interface ValidateUserRequest {
    @Nullable
    String email();
}
