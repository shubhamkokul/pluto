package castiel.solutionbyhour.processor.user;

import castiel.solutionbyhour.exception.UserAlreadyExistsException;
import castiel.solutionbyhour.model.data.UserEntity;
import castiel.solutionbyhour.model.user.CreateUserInput;
import castiel.solutionbyhour.model.web.BaseResponse;
import castiel.solutionbyhour.model.web.ImmutableBaseResponse;
import castiel.solutionbyhour.persistence.AuthenticationRepository;
import castiel.solutionbyhour.persistence.UserRepository;
import castiel.solutionbyhour.processor.user.transformer.UserTransformer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.function.Function;

@ApplicationScoped
public class CreateUser implements Function<CreateUserInput, BaseResponse<UserEntity>> {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final UserTransformer userTransformer;

    @Inject
    public CreateUser(UserRepository userRepository,
                      AuthenticationRepository authenticationRepository, UserTransformer userTransformer) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.userTransformer = userTransformer;
    }

    @Override
    public BaseResponse<UserEntity> apply(CreateUserInput createUserInput) {
        try {
            UserEntity userEntity = userRepository
                    .createUser(userTransformer.apply(createUserInput));
            return ImmutableBaseResponse.<UserEntity>builder()
                    .response(userEntity)
                    .message("Created a new user")
                    .build();
        } catch (UserAlreadyExistsException exception) {
            return ImmutableBaseResponse.<UserEntity>builder()
                    .message(exception.getMessage())
                    .build();
        }
    }
}
