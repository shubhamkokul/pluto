package castiel.solutionbyhour.processor.authentication.transformer;

import castiel.solutionbyhour.model.data.User;
import castiel.solutionbyhour.model.incoming.authentication.Credential;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.function.Function;

@ApplicationScoped
public class UserTransformer implements Function<Credential, User> {

    private static final Logger LOGGER = Logger.getLogger(UserTransformer.class);

    @Override
    public User apply(Credential credential) {
        if (credential == null) {
            LOGGER.warn("Received null credential, returning null user.");
            return null;
        }

        User user = new User();
        user.name = credential.name();
        user.email = credential.email();
        user.username = credential.username();

        LOGGER.debugf("Transformed credential to user: %s", user);
        return user;
    }
}
