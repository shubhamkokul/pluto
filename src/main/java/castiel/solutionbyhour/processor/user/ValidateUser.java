package castiel.solutionbyhour.processor.user;

import castiel.solutionbyhour.model.data.UserEntity;
import castiel.solutionbyhour.model.user.validateuser.ImmutableValidateUserResponse;
import castiel.solutionbyhour.model.user.validateuser.ValidateUserRequest;
import castiel.solutionbyhour.model.user.validateuser.ValidateUserResponse;
import castiel.solutionbyhour.model.web.BaseResponse;
import castiel.solutionbyhour.model.web.ImmutableBaseResponse;
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

    public BaseResponse<ValidateUserResponse> process(String username, String email) {
        Optional<UserEntity> userEntity = Optional.ofNullable(
            userRepository.findByUsernameOrEmail(username, email)
    );
        return ImmutableBaseResponse.<ValidateUserResponse>builder().response(ImmutableValidateUserResponse.builder()
                .existingUser(userEntity.isPresent())
                .build())
                .build();
    }
}
