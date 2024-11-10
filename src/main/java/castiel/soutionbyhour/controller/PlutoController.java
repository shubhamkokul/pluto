package castiel.soutionbyhour.controller;

import castiel.soutionbyhour.delegate.FinHubDelegate;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class PlutoController implements PlutoEndpoint {

    @Inject
    FinHubDelegate finHubDelegate;

    @Override
    @GET
    @Path("/quote/{ticker}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getQuote(@PathParam("ticker") String ticker) {
        return finHubDelegate
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
        }).onFailure().recoverWithItem(failure -> {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error occurred while fetching quote: " + failure.getMessage())
                    .build();
        });
    }
}
