package castiel.solutionbyhour.model.auth.createtoken;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateAuthTokenRequest.class)
public interface CreateAuthTokenRequest {
    @Nullable
    String username();
    @Nullable
    String email();
    String password();
}
