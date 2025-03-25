package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.dtos.DeviceDTO;

public class DeviceBuilder {

    // Convert a Device entity to a DeviceDTO
    public static DeviceDTO toDeviceDTO(Device device) {
        if (device == null) {
            return null;
        }
        return new DeviceDTO(device.getId(), device.getMaxConsumption(), device.getUserID());
    }

    // Convert a DeviceDTO to a Device entity
    public static Device toDevice(DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            return null;
        }
        Device device = new Device();
        device.setId(deviceDTO.getId());
        device.setMaxConsumption(deviceDTO.getMaxConsumption());
        device.setUserID(deviceDTO.getUserID());
        return device;
    }
}
