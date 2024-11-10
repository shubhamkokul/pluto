package castiel.soutionbyhour.model.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Authentication extends PanacheEntity {

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    public User user;  // Link to the User entity

    @Column(nullable = false)
    public String passwordHash;

    @Column(nullable = false)
    public String salt;

    @Column
    public LocalDateTime lastLogin;

    @Column(nullable = false)
    public Integer failedAttempts = 0;

    @Column
    public LocalDateTime lockedUntil;

    @Column(nullable = false, unique = true)
    public String jwtToken;  // JWT token
}