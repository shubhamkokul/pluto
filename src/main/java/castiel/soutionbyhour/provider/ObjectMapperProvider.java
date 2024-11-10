package castiel.soutionbyhour.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ObjectMapperProvider {

    private final ObjectMapper objectMapper;

    public ObjectMapperProvider() {
        this.objectMapper = new ObjectMapper();
    }

    @Produces
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
