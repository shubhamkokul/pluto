package castiel.solutionbyhour.controller;

import castiel.solutionbyhour.core.auth.AuthRequired;
import castiel.solutionbyhour.delegate.QuoteDelegate;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class QuoteController implements PlutoEndpoint {

    @Inject
    QuoteDelegate quoteDelegate;

    @GET
    @Path("/quote/{ticker}")
    @Produces(MediaType.APPLICATION_JSON)
    @AuthRequired
    public Uni<Response> getQuote(@PathParam("ticker") String ticker) {
        return quoteDelegate
                .getQuote(ticker)
                .onItem()
                .transform(quote -> {
            if (quote == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("Quote not found for ticker: " + ticker)
                        .build();
            } else {
                return Response
                        .ok(quote)
                        .build();
            }
        }).onFailure().recoverWithItem(failure -> Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error occurred while fetching quote: " + failure.getMessage())
                .build());
    }
}
