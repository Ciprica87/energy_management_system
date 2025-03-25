package ro.tuc.ds2020.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DeviceMessage;

@Service
public class MessageSenderService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendMessage(DeviceMessage deviceMessage) throws Exception {
        String jsonMessage = objectMapper.writeValueAsString(deviceMessage);
        rabbitTemplate.convertAndSend("device_queue", jsonMessage); // Replace "device_queue" with your queue name if different
    }



}
