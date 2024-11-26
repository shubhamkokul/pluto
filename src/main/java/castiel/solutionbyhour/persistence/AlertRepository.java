package castiel.solutionbyhour.persistence;

import castiel.solutionbyhour.model.data.AlertEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AlertRepository {

    @Transactional
    public AlertEntity createAlert(AlertEntity alertEntity) {
        AlertEntity.persist(alertEntity);
        return alertEntity;
    }

    public AlertEntity findById(Long id) {
        return AlertEntity.findById(id);
    }

    public List<AlertEntity> findAllAlerts() {
        return AlertEntity.listAll();
    }

    public List<AlertEntity> findByCustomerId(Long customerId) {
        return AlertEntity.list("customerId", customerId);
    }

    @Transactional
    public void updateAlert(AlertEntity alertEntity) {
        AlertEntity existingAlert = AlertEntity.findById(alertEntity.alertId);
        if (existingAlert != null) {
            existingAlert.customerId = alertEntity.customerId;
            existingAlert.ticker = alertEntity.ticker;
            existingAlert.email = alertEntity.email;
            existingAlert.alertChangeForm = alertEntity.alertChangeForm;
            existingAlert.percentChange = alertEntity.percentChange;
            existingAlert.status = alertEntity.status;
            existingAlert.persist();
        }
    }

    @Transactional
    public boolean deleteById(Long id) {
        return AlertEntity.deleteById(id);
    }

    public long countAlerts() {
        return AlertEntity.count();
    }

    public List<AlertEntity> findActiveAlerts() {
        return AlertEntity.list("status", true);
    }
}
