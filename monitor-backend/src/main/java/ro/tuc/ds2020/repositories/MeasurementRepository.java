package ro.tuc.ds2020.repositories;

import ro.tuc.ds2020.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {
    // Custom query methods can be defined here

    // Example: Find measurements by device ID
    // List<MeasurementMessageBroker> findByDeviceId(UUID deviceId);
}
