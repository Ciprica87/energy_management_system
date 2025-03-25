package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceMessage;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;

import java.util.UUID;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        if(device.getUser() == null){
            return new DeviceDTO(device.getId(), device.getName(), device.getMaxConsumption());
        } else {
            return new DeviceDTO(device.getId(), device.getName(), device.getMaxConsumption(), device.getUser().getId());
        }
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getName(), deviceDTO.getMaxConsumption());
    }

    public static DeviceMessage toDeviceMessage(Device device) {
        return new DeviceMessage(device.getId(), device.getMaxConsumption(), device.getUser().getId());
    }
}
