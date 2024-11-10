package castiel.soutionbyhour.model.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class User extends PanacheEntity {

    @Column(name = "customer_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long customerId;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String username;

    @Column(nullable = false, unique = true)
    public String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_address_id")
    public Address primaryAddress;
}
