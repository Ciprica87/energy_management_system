package ro.tuc.ds2020.services.brokers;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceMessage;
import ro.tuc.ds2020.services.DeviceService;

public class DeviceMessageBroker implements MessageListener {

    private final DeviceService deviceService;

    public DeviceMessageBroker(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void onMessage(Message message) {
        try {
            String jsonMessage = new String(message.getBody());
            System.out.println("Received message: " + jsonMessage);

            ObjectMapper objectMapper = new ObjectMapper();
            DeviceMessage deviceMessage = objectMapper.readValue(jsonMessage, DeviceMessage.class);

            switch (deviceMessage.getRequest().toUpperCase()) {
                case "CREATE":
                    DeviceDTO deviceDTO = new DeviceDTO(deviceMessage.getId(), deviceMessage.getMaxConsumption(), deviceMessage.getUserId());
                    deviceService.insert(deviceDTO);
                    break;
                case "DELETE":
                    deviceService.delete(deviceMessage.getId());
                    break;
                default:
                    System.err.println("Unknown command: " + deviceMessage.getRequest());
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}