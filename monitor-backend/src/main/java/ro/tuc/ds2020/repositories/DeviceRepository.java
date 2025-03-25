package ro.tuc.ds2020.repositories;

import ro.tuc.ds2020.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    // Custom query methods can be defined here
}
