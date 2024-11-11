package castiel.solutionbyhour.delegate;

import castiel.solutionbyhour.exception.FetchException;
import castiel.solutionbyhour.model.finhub.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class FinHubDelegate {

    private static final Logger LOGGER = Logger.getLogger(FinHubDelegate.class);
    private static final String SYMBOL = "symbol";
    private static final String FINHUB_AUTH = "X-Finnhub-Token";
    public static final String QUOTE = "/quote";

    private final Client client;
    private final ObjectMapper objectMapper;

    @ConfigProperty(name = "baseurl")
    private String baseUrl;

    @ConfigProperty(name = "apikey")
    private String apikey;

    @Inject
    public FinHubDelegate(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    /**
     * Get the stock quote for the given ticker symbol asynchronously.
     *
     * @param ticker The ticker symbol.
     * @return A Uni wrapping the result or an error.
     */
    public Uni<Quote> getQuote(String ticker) {
        // Validate input parameters
        if (isTickerNullOrEmpty(ticker)) {
            return handleError("Ticker symbol is null or empty");
        }

        // Validate configuration properties
        if (isInvalidConfiguration()) {
            return handleError("API configuration is incomplete");
        }

        // Use reactive invocation to make the request
        return Uni.createFrom().completionStage(() ->
                        triggerRequest(ticker))
                .onItem()
                .transformToUni(response -> handleResponse(response, ticker))
                .onFailure()
                .invoke(th -> LOGGER.error("Error occurred while fetching quote for " + ticker, th))
                .onFailure()
                .transform(th -> new FetchException("Error fetching quote for " + ticker, th));
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
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            LOGGER.info("Successfully fetched quote for " + ticker);
            String quote = response.readEntity(String.class);
            LOGGER.debug("Quote data: " + quote);
            try {
                // Deserialize and return the quote Todo: 'quarkus-rest-client-jackson' unable to deserialize
                return Uni.createFrom().item(objectMapper.readValue(quote, Quote.class));
            } catch (JsonProcessingException e) {
                return handleError("Failed to deserialize response for ticker: " + ticker, e);
            }
        } else {
            return handleError("Failed to fetch quote for " + ticker + ". HTTP Status: " + response.getStatus());
        }
    }

    private Uni<Quote> handleError(String errorMessage) {
        LOGGER.error(errorMessage);
        return Uni.createFrom().failure(new FetchException(errorMessage));
    }

    private Uni<Quote> handleError(String errorMessage, Throwable e) {
        LOGGER.error(errorMessage, e);
        return Uni.createFrom().failure(new FetchException(errorMessage, e));
    }

    private boolean isTickerNullOrEmpty(String ticker) {
        return ticker == null || ticker.trim().isEmpty();
    }

    private boolean isInvalidConfiguration() {
        return Objects.isNull(baseUrl) || Objects.isNull(apikey) || baseUrl.trim().isEmpty() || apikey.trim().isEmpty();
    }
}