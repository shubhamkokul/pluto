package castiel.solutionbyhour.processor.authentication;

import castiel.solutionbyhour.model.data.User;
import castiel.solutionbyhour.model.incoming.authentication.Credential;
import castiel.solutionbyhour.persistence.AuthenticationRepository;
import castiel.solutionbyhour.persistence.UserRepository;
import castiel.solutionbyhour.processor.authentication.transformer.UserTransformer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.function.Function;

@ApplicationScoped
public class AuthenticationProcessor implements Function<Credential, User> {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final UserTransformer userTransformer;

    @Inject
    public AuthenticationProcessor(UserRepository userRepository,
                                   AuthenticationRepository authenticationRepository, UserTransformer userTransformer) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.userTransformer = userTransformer;
    }

    @Override
    public User apply(Credential credential) {
        //Create Authentication JWT
        //Insert into Authentication

        return userRepository
                .createUser(userTransformer.apply(credential));
    }
}
