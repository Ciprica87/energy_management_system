package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceMessage;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final UserService userService;
    private final MessageSenderService messageSenderService;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserService userService, MessageSenderService messageSenderService) {
        this.deviceRepository = deviceRepository;
        this.userService = userService;
        this.messageSenderService = messageSenderService;
    }

    public List<DeviceDTO> findDevices() {
        List<Device> personList = deviceRepository.findAll();
        return personList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public Device findDeviceById(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
        }
        return prosumerOptional.get();
    }

    public DeviceDTO findDeviceByIdDTO(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
        }
        return DeviceBuilder.toDeviceDTO(prosumerOptional.get());
    }

    public UUID insert(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Person with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public DeviceDTO update(UUID id, DeviceDTO updatedDeviceDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in the database", id);
            // You can throw an exception or return an appropriate response here.
        }

        Device existingDevice = deviceOptional.get();
        Device updatedDevice = DeviceBuilder.toEntity(updatedDeviceDTO);

        // Update the fields of the existing device with the values from the updated device.
        existingDevice.setName(updatedDevice.getName());
        existingDevice.setMaxConsumption(updatedDevice.getMaxConsumption());
        // Update other fields as needed.

        existingDevice = deviceRepository.save(existingDevice);

        return DeviceBuilder.toDeviceDTO(existingDevice);
    }

    public Device delete(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in the database", id);
            // You can throw an exception or return an appropriate response here.
        }

        deviceRepository.delete(deviceOptional.get());
        LOGGER.debug("Device with id {} was deleted from the database", id);

        return deviceOptional.get();
    }

    public void mapUserToDevice(UUID userId, UUID deviceId){
        User user = userService.findUserById(userId);
        Device device = findDeviceById(deviceId);
        System.out.println(user.getId() + " " + device.getId());
        //save the updated device
        //user.addDevice(device);
        device.setUser(user);
        deviceRepository.save(device);
        userService.insert(user);
        //send the message to the queue
        DeviceMessage deviceMessage = new DeviceMessage(device.getId(), device.getMaxConsumption(), device.getUser().getId());
        deviceMessage.setRequest("CREATE");
        try {
            messageSenderService.sendMessage(deviceMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void unmapUserFromDevice(UUID userId, UUID deviceId){
        User user = userService.findUserById(userId);
        Device device = findDeviceById(deviceId);
        DeviceMessage deviceMessage = new DeviceMessage(device.getId(), device.getMaxConsumption(), device.getUser().getId());
        deviceMessage.setRequest("DELETE");
        try {
            messageSenderService.sendMessage(deviceMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //user.removeDevice(device);
        device.setUser(null);
        deviceRepository.save(device);
        userService.insert(user);

    }


}
