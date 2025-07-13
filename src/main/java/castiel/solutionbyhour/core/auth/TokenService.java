package castiel.solutionbyhour.core.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import castiel.solutionbyhour.model.auth.ImmutableTokenContext;
import castiel.solutionbyhour.model.auth.TokenContext;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TokenService {

    @ConfigProperty(name = "application.name")
    private String name;

    @Inject
    JWTParser jwtParser;


    public String createAuthToken(String email) {
        Instant now = Instant.now();
        return Jwt.issuer(name)
                .upn(email)
                .expiresAt(now.plus(100, ChronoUnit.DAYS))
                .issuedAt(now)
                .sign();
    }

    public TokenContext validateToken(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            if (!name.equals(jwt.getIssuer())) {
                return ImmutableTokenContext.builder().result(false).build();
            }
            if (jwt.getExpirationTime() > 0 && Instant.now().isAfter(Instant.ofEpochSecond(jwt.getExpirationTime()))) {
                return ImmutableTokenContext.builder().result(false).build();
            }
            String email = jwt.getName();
            return ImmutableTokenContext.builder().result(true).email(email).build();
        } catch (ParseException e) {
            return ImmutableTokenContext.builder().result(false).build();
        }
    }
}
