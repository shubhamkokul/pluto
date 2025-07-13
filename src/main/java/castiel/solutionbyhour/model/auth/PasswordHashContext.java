package castiel.solutionbyhour.model.auth;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize(as = ImmutablePasswordHashContext.class)
public interface PasswordHashContext {
    String generatedSalt();

    String hashedPassword();
}
