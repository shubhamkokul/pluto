package castiel.soutionbyhour.model.alphavantage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import org.immutables.value.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@JsonDeserialize(as = ImmutableInformation.class)
public interface Information {

    @JsonProperty("Information")
    @Nullable
    String information();
}
