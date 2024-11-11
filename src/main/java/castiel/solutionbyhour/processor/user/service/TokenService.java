package castiel.solutionbyhour.processor.user.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class TokenService {

    @ConfigProperty(name = "application.name")
    private String name;


    public String createAuthToken(String username) {
        Instant now = Instant.now();
        return Jwt.issuer(name)
                .upn(username)
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .issuedAt(now)
                .sign();
    }
}
