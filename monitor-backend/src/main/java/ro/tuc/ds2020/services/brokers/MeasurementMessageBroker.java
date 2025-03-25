package ro.tuc.ds2020.services.brokers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.amqp.core.MessageListener;
import ro.tuc.ds2020.dtos.MeasurementDTO;
import ro.tuc.ds2020.dtos.MeasurementMessage;
import ro.tuc.ds2020.services.MeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.UUID;


public class MeasurementMessageBroker implements MessageListener {

    private final MeasurementService measurementService;

    public MeasurementMessageBroker(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Assuming the body of the message is a JSON string
            String jsonMessage = new String(message.getBody());
            System.out.println("Received message: " + jsonMessage);

            // Convert JSON string to EnergyData object
            MeasurementMessage measurementMessage = objectMapper.readValue(jsonMessage, MeasurementMessage.class);


            MeasurementDTO messageDTO = new MeasurementDTO(measurementMessage.getId(), measurementMessage.getValue(), measurementMessage.getTimestamp(), measurementMessage.getDeviceId());
            messageDTO.setId(UUID.randomUUID());
            measurementService.insert(messageDTO);


        } catch (IOException e) {
            // Handle the exception properly - this could be logging or even requeuing the message
            System.err.println("Failed to convert message to EnergyData object: " + e.getMessage());
        }
    }
}
