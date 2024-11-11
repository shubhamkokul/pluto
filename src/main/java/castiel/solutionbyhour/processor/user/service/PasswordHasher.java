package castiel.solutionbyhour.processor.user.service;

import castiel.solutionbyhour.model.user.PasswordHashContext;
import castiel.solutionbyhour.model.user.ImmutablePasswordHashContext;
import jakarta.enterprise.context.ApplicationScoped;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Optional;

@ApplicationScoped
public class PasswordHasher {

    private static final int HASH_LENGTH = 256; // 256 bits (32 bytes)
    private static final int ITERATIONS = 10000;
    public static final String PBKDF_2_WITH_HMAC_SHA_256 = "PBKDF2WithHmacSHA256";

    public Optional<PasswordHashContext> hashPassword(String password) {
        try {
            String generatedSalt = SaltGenerator.generateSalt();
            byte[] saltBytes = Base64.getDecoder().decode(generatedSalt);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, HASH_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF_2_WITH_HMAC_SHA_256);
            byte[] hashBytes = keyFactory.generateSecret(spec).getEncoded();
            return Optional.of(ImmutablePasswordHashContext.builder()
                    .generatedSalt(generatedSalt)
                    .hashedPassword(Base64.getEncoder().encodeToString(hashBytes))
                    .build());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Error while hashing password: " + e.getMessage());
            return Optional.empty();
        }
    }
}
