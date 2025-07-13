package castiel.solutionbyhour.persistence;

import castiel.solutionbyhour.model.data.AuthenticationEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class AuthenticationRepository {

    // Save or update authentication data
    @Transactional
    public AuthenticationEntity createAuthentication(AuthenticationEntity authenticationEntity) {
        authenticationEntity.persist();
        return authenticationEntity;
    }

    // Find authentication by user ID
    public AuthenticationEntity findByCustomerId(Long customerId) {
        return AuthenticationEntity.find("customerId", customerId).firstResult();
    }
}
