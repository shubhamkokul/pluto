package castiel.solutionbyhour.delegate;

import castiel.solutionbyhour.exception.FetchException;
import castiel.solutionbyhour.model.finhub.Quote;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.Objects;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class QuoteDelegate {

    private static final Logger LOGGER = Logger.getLogger(QuoteDelegate.class);
    private static final String SYMBOL = "symbol";
    private static final String FINHUB_AUTH = "X-Finnhub-Token";
    public static final String QUOTE = "/quote";

    private final Client client;

    @ConfigProperty(name = "baseurl")
    String baseUrl;

    @ConfigProperty(name = "apikey")
    String apikey;

    @Inject
    public QuoteDelegate(Client client) {
        this.client = client;
    }

    /**
     * Get the stock quote for the given ticker symbol asynchronously.
     *
     * @param ticker The ticker symbol.
     * @return A Uni wrapping the result or an error.
     */
    public Uni<Quote> getQuote(String ticker) {
        if (isTickerNullOrEmpty(ticker)) {
            return handleError("Ticker symbol is null or empty");
        }

        if (isInvalidConfiguration()) {
            return handleError("API configuration is incomplete");
        }

        return Uni.createFrom().completionStage(() -> triggerRequest(ticker))
                .onItem().transformToUni(response -> handleResponse(response, ticker))
                .onFailure().invoke(th -> LOGGER.error("Error occurred while fetching quote for " + ticker, th))
                .onFailure().transform(th -> new FetchException("Error fetching quote for " + ticker, th));
    }

    private CompletionStage<Response> triggerRequest(String ticker) {
        return client.target(baseUrl)
                .path(QUOTE)
                .queryParam(SYMBOL, ticker)
                .request()
                .header(FINHUB_AUTH, apikey)
                .rx()
                .get();
    }

    private Uni<Quote> handleResponse(Response response, String ticker) {
        try (response) { // Auto-close response to avoid leaks
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                LOGGER.info("Successfully fetched quote for " + ticker);
                Quote quoteJson = response.readEntity(Quote.class);
                LOGGER.infof("Response body for %s: %s", ticker, quoteJson);
                return Uni.createFrom().item(quoteJson);
            } else {
                return handleError("Failed to fetch quote for " + ticker + ". HTTP Status: " + response.getStatus());
            }
        }
    }

    private Uni<Quote> handleError(String errorMessage) {
        LOGGER.error(errorMessage);
        return Uni.createFrom().failure(new FetchException(errorMessage));
    }

    private boolean isTickerNullOrEmpty(String ticker) {
        return ticker == null || ticker.trim().isEmpty();
    }

    private boolean isInvalidConfiguration() {
        return Objects.isNull(baseUrl) || Objects.isNull(apikey) || baseUrl.trim().isEmpty() || apikey.trim().isEmpty();
    }
}
