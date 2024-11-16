package castiel.solutionbyhour.processor.user;

import castiel.solutionbyhour.model.data.UserEntity;
import castiel.solutionbyhour.model.user.validateuser.ImmutableValidateUserResponse;
import castiel.solutionbyhour.model.user.validateuser.ValidateUserRequest;
import castiel.solutionbyhour.model.user.validateuser.ValidateUserResponse;
import castiel.solutionbyhour.persistence.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class ValidateUser {

    private final UserRepository userRepository;

    @Inject
    public ValidateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ValidateUserResponse validateUserResponse(ValidateUserRequest validateUserRequest) {
        Optional<UserEntity> userEntity = Optional.ofNullable(
            userRepository.findByUsernameOrEmail(validateUserRequest.username(), validateUserRequest.email())
    );
        return ImmutableValidateUserResponse.builder()
                .existingUser(userEntity.isPresent())
                .build();
    }
}
