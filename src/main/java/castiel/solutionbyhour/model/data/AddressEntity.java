package castiel.solutionbyhour.model.data;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class AddressEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    public Long addressId;

    @Column(name = "customer_id", nullable = false)
    public Long customerId;

    @Column(name = "street", nullable = false, length = 255)
    public String street;

    @Column(name = "city", nullable = false, length = 255)
    public String city;

    @Column(name = "state", length = 255)
    public String state;

    @Column(name = "postal_code", length = 20)
    public String postalCode;

    @Column(name = "country", length = 255)
    public String country;

    @Column(name = "is_primary", nullable = false)
    public Boolean isPrimary = false;
}

