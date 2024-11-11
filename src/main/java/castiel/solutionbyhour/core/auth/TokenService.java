package castiel.solutionbyhour.core.auth;

import castiel.solutionbyhour.model.auth.ImmutableTokenContext;
import castiel.solutionbyhour.model.auth.TokenContext;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class TokenService {

    @ConfigProperty(name = "application.name")
    private String name;

    @Inject
    JWTParser jwtParser;


    public String createAuthToken(String username) {
        Instant now = Instant.now();
        return Jwt.issuer(name)
                .upn(username)
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
            String username = jwt.getName();
            return ImmutableTokenContext.builder().result(true).username(username).build();
        } catch (ParseException e) {
            return ImmutableTokenContext.builder().result(false).build();
        }
    }
}
