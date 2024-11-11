package castiel.solutionbyhour.provider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

@ApplicationScoped
public class WebClientProvider {

    private final Client client;

    public WebClientProvider() {
        this.client = ClientBuilder.newClient();
    }

    @Produces
    public Client getClient() {
        return client;
    }
}