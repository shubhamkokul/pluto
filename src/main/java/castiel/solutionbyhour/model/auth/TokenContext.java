package castiel.solutionbyhour.model.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonDeserialize(as = ImmutableTokenContext.class)
public interface TokenContext {
    boolean result();
    Optional<String> username();
}
