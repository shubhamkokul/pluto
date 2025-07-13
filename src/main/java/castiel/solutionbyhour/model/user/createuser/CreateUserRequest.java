package castiel.solutionbyhour.model.user.createuser;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.smallrye.common.constraint.NotNull;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateUserRequest.class)
public interface CreateUserRequest {
    @NotNull
    @JsonProperty("first_name")
    String firstName();
    @NotNull
    @JsonProperty("last_name")
    String lastName();
    @NotNull
    @JsonProperty("password")
    String password();
    @NotNull
    @JsonProperty("email")
    String email();
}
