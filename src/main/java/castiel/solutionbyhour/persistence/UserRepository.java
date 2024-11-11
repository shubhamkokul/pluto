package castiel.solutionbyhour.persistence;

import castiel.solutionbyhour.exception.UserAlreadyExistsException;
import castiel.solutionbyhour.model.data.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    @Transactional
    public UserEntity createUser(UserEntity userEntity) throws UserAlreadyExistsException {
        UserEntity existingUserEntity = findByUsernameOrEmail(userEntity.email, userEntity.username);
        if (existingUserEntity != null) {
            throw new UserAlreadyExistsException("A user with the same username or email already exists.");
        }
        userEntity.persist();
        return userEntity;
    }

    public UserEntity findByUsernameOrEmail(String email, String username) {
        return UserEntity.find("SELECT u FROM UserEntity u WHERE u.username = ?1 OR u.email = ?2", username, email).firstResult();
    }

    public Optional<UserEntity> findByUsername(String username) {
        return UserEntity.find("username", username).firstResultOptional();
    }

    public Optional<UserEntity> findByEmail(String email) {
        return UserEntity.find("email", email).firstResultOptional();
    }

    public Optional<UserEntity> findByCustomerId(String customerId) {
        return UserEntity.find("customer_id", customerId).firstResultOptional();
    }
}
