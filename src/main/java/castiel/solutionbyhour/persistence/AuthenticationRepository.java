package castiel.solutionbyhour.persistence;

import castiel.solutionbyhour.model.data.AuthenticationEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class AuthenticationRepository {

    // Save or update authentication data
    @Transactional
    public AuthenticationEntity createAuthentication(AuthenticationEntity authenticationEntity) {
        authenticationEntity.persist();  // Persist authentication data for a user
        return authenticationEntity;
    }

    // Find authentication by user ID
    public Optional<AuthenticationEntity> findByCustomerId(Long customerId) {
        return AuthenticationEntity.find("customer_id", customerId).firstResultOptional();
    }

    // Find authentication by JWT token
    public Optional<AuthenticationEntity> findByJwtToken(String jwtToken) {
        return AuthenticationEntity.find("jwtToken", jwtToken).firstResultOptional();
    }
}
