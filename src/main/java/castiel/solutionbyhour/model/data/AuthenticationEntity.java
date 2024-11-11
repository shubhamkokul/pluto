package castiel.solutionbyhour.model.data;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "authentication")
public class AuthenticationEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authentication_id")
    public Long authenticationId;

    @Column(name = "customer_id", nullable = false)
    public Long customerId;

    @Column(name = "password_hash", nullable = false)
    public String passwordHash;

    @Column(name = "salt", nullable = false)
    public String salt;

    @Column(name = "last_login")
    public LocalDateTime lastLogin;

    @Column(name = "failed_attempts", nullable = false)
    public Integer failedAttempts = 0;

    @Column(name = "locked_until")
    public LocalDateTime lockedUntil;
}