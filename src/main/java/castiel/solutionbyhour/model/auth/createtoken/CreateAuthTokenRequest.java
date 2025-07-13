package castiel.solutionbyhour.model.auth.createtoken;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.annotation.Nullable;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateAuthTokenRequest.class)
public interface CreateAuthTokenRequest {
    @Nullable
    String email();

    String password();
}
