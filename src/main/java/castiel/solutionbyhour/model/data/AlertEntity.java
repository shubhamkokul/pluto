package castiel.solutionbyhour.model.data;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "alert")
public class AlertEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    public Long alertId;

    @Column(name = "customer_id", nullable = false)
    public Long customerId;

    @Column(name = "ticker", length = 255)
    public String ticker;

    @Column(name = "email", length = 255)
    public String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_change_form", length = 50)
    public AlertChangeForm alertChangeForm;

    @Column(name = "percent_change")
    public Float percentChange;

    @Column(name = "status")
    public Boolean status;
}
