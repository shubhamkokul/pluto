package castiel.soutionbyhour.controller;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/pluto")
public interface PlutoEndpoint {

    @GET
    @Path("/quote/{ticker}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getQuote(@PathParam("ticker") String ticker);
}