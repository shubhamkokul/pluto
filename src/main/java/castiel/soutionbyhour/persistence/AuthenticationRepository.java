package castiel.soutionbyhour.persistence;

import castiel.soutionbyhour.model.data.Authentication;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class AuthenticationRepository {

    // Save or update authentication data
    @Transactional
    public Authentication createAuthentication(Authentication authentication) {
        authentication.persist();  // Persist authentication data for a user
        return authentication;
    }

    // Find authentication by user ID
    public Optional<Authentication> findByCustomerId(Long customerId) {
        return Authentication.find("customer_id", customerId).firstResultOptional();
    }

    // Find authentication by JWT token
    public Optional<Authentication> findByJwtToken(String jwtToken) {
        return Authentication.find("jwtToken", jwtToken).firstResultOptional();
    }
}
