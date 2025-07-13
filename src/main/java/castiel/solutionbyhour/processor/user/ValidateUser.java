package castiel.solutionbyhour.processor.user;

import java.util.Optional;

import castiel.solutionbyhour.model.data.UserEntity;
import castiel.solutionbyhour.model.user.validateuser.ImmutableValidateUserResponse;
import castiel.solutionbyhour.model.user.validateuser.ValidateUserResponse;
import castiel.solutionbyhour.model.web.BaseResponse;
import castiel.solutionbyhour.model.web.ImmutableBaseResponse;
import castiel.solutionbyhour.persistence.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ValidateUser {

    private final UserRepository userRepository;

    @Inject
    public ValidateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BaseResponse<ValidateUserResponse> process(String email) {
        Optional<UserEntity> userEntity = 
            userRepository.findByEmail(email);
            
        return ImmutableBaseResponse.<ValidateUserResponse>builder().data(ImmutableValidateUserResponse.builder()
                .existingUser(userEntity.isPresent())
                .build())
                .build();
    }
}
