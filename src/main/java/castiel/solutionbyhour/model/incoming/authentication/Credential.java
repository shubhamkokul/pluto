package castiel.solutionbyhour.model.incoming.authentication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCredential.class)
public interface Credential {
    String name();
    String username();
    String password();
    String email();
}
