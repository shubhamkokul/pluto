package castiel.solutionbyhour.persistence;

import castiel.solutionbyhour.model.data.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    @Transactional
    public User createUser(User user) {
        user.persist();
        return user;
    }

    public Optional<User> findByUsername(String username) {
        return User.find("username", username).firstResultOptional();
    }

    public Optional<User> findByEmail(String email) {
        return User.find("email", email).firstResultOptional();
    }

    public Optional<User> findByCustomerId(String customerId) {
        return User.find("customer_id", customerId).firstResultOptional();
    }
}
