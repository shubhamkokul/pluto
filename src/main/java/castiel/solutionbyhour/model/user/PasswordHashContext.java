package castiel.solutionbyhour.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutablePasswordHashContext.class)
public interface PasswordHashContext {
    String generatedSalt();
    String hashedPassword();
}
