package castiel.solutionbyhour.model.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateUserInput.class)
public interface CreateUserInput {
    String name();
    String username();
    String password();
    String email();
}
