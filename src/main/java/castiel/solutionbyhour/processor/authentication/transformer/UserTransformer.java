package castiel.solutionbyhour.processor.authentication.transformer;

import castiel.solutionbyhour.model.data.UserEntity;
import castiel.solutionbyhour.model.user.CreateUserInput;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.function.Function;

@ApplicationScoped
public class UserTransformer implements Function<CreateUserInput, UserEntity> {

    private static final Logger LOGGER = Logger.getLogger(UserTransformer.class);

    @Override
    public UserEntity apply(CreateUserInput createUserInput) {
        if (createUserInput == null) {
            LOGGER.warn("Received null credential, returning null user.");
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.name = createUserInput.name();
        userEntity.email = createUserInput.email();
        userEntity.username = createUserInput.username();

        LOGGER.debugf("Transformed credential to user: %s", userEntity);
        return userEntity;
    }
}
