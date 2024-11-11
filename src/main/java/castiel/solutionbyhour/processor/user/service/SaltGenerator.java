package castiel.solutionbyhour.processor.user.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;
import java.util.Base64;

@ApplicationScoped
public class SaltGenerator {
    private static final int SALT_LENGTH = 16; // 16 bytes = 128 bits

    public static String generateSalt() {
        // SecureRandom instance for cryptographically strong random number generation
        SecureRandom secureRandom = new SecureRandom();

        // Create a byte array for the salt
        byte[] salt = new byte[SALT_LENGTH];

        // Fill the byte array with random bytes
        secureRandom.nextBytes(salt);

        // Encode salt as Base64 for easy storage and retrieval
        return Base64.getEncoder().encodeToString(salt);
    }
}
