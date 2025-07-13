package castiel.solutionbyhour.model.auth;

import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize(as = ImmutableTokenContext.class)
public interface TokenContext {
    boolean result();
    Optional<String> email();
}
