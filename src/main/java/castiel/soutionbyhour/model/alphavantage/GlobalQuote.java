package castiel.soutionbyhour.model.alphavantage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import org.immutables.value.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@JsonDeserialize(as = ImmutableGlobalQuote.class)
public interface GlobalQuote extends Information {

    @JsonProperty("01. symbol")
    @Nullable
    String symbol();

    @JsonProperty("02. open")
    @Nullable
    String open();

    @JsonProperty("03. high")
    @Nullable
    String high();

    @JsonProperty("04. low")
    @Nullable
    String low();

    @JsonProperty("05. price")
    @Nullable
    String price();

    @JsonProperty("06. volume")
    @Nullable
    String volume();

    @JsonProperty("07. latest trading day")
    @Nullable
    String latestTradingDay();

    @JsonProperty("08. previous close")
    @Nullable
    String previousClose();

    @JsonProperty("09. change")
    @Nullable
    String change();

    @JsonProperty("10. change percent")
    @Nullable
    String changePercent();
}