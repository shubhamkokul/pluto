package castiel.solutionbyhour.model.finhub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@JsonDeserialize(as = ImmutableQuote.class)
public interface Quote {

    @JsonProperty("c")
    double currentPrice();

    @JsonProperty("d")
    double change();

    @JsonProperty("dp")
    double changePercent();

    @JsonProperty("h")
    double high();

    @JsonProperty("l")
    double low();

    @JsonProperty("o")
    double open();

    @JsonProperty("pc")
    double previousClose();

    @JsonProperty("t")
    long timestamp();
}