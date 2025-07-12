package castiel.solutionbyhour.model.web;

import castiel.solutionbyhour.core.serializer.OptionalSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonDeserialize(as = ImmutableBaseResponse.class)
@JsonInclude(JsonInclude.Include.NON_ABSENT) // Ignore fields that are absent (i.e., Optional.empty()) in the serialized output.
public interface BaseResponse<T> {
    @JsonProperty("data")
    @JsonSerialize(using = OptionalSerializer.class)
    Optional<T> data();

    @JsonProperty("message")
    @JsonSerialize(using = OptionalSerializer.class)
    Optional<String> message();
}
