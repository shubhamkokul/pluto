package castiel.solutionbyhour.persistence;

import java.util.Optional;

import castiel.solutionbyhour.exception.UserAlreadyExistsException;
import castiel.solutionbyhour.model.data.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserRepository {

    @Transactional
    public UserEntity createUser(UserEntity userEntity) throws UserAlreadyExistsException {
        Optional<UserEntity> existingUserEntity = findByEmail(userEntity.email);
        if (existingUserEntity.isPresent()) {
            throw new UserAlreadyExistsException("A user with the email already exists.");
        }
        userEntity.persist();
        return userEntity;
    }
    

    public Optional<UserEntity> findByEmail(String email) {
        return UserEntity.find("email", email).firstResultOptional();
    }

    public Optional<UserEntity> findByCustomerId(String customerId) {
        return UserEntity.find("customerId", customerId).firstResultOptional();
    }
}
