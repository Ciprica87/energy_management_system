package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceDTO> findAllDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO findDeviceById(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            return null; // Or throw an exception
        }
        return DeviceBuilder.toDeviceDTO(deviceOptional.get());
    }

    public void insert(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toDevice(deviceDTO);

        deviceRepository.save(device);
    }

    public DeviceDTO update(UUID id, DeviceDTO updatedDeviceDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in the database", id);
            return null; // Or throw an exception
        }

        Device existingDevice = deviceOptional.get();
        Device updatedDevice = DeviceBuilder.toDevice(updatedDeviceDTO);

        // Update the fields of the existing device with the values from the updated device.
        existingDevice.setMaxConsumption(updatedDevice.getMaxConsumption());
        existingDevice.setUserID(updatedDevice.getUserID());

        existingDevice = deviceRepository.save(existingDevice);

        return DeviceBuilder.toDeviceDTO(existingDevice);
    }

    public void delete(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in the database", id);
            // You can throw an exception or return an appropriate response here.
            return;
        }

        deviceRepository.delete(deviceOptional.get());
        LOGGER.debug("Device with id {} was deleted from the database", id);
    }

    public Device findDeviceByIdObject(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            return null; // Or throw an exception
        }
        return deviceOptional.get();
    }
}
