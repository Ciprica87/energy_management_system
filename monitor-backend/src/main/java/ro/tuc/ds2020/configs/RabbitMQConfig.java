package ro.tuc.ds2020.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.MeasurementService;
import ro.tuc.ds2020.services.brokers.DeviceMessageBroker;
import ro.tuc.ds2020.services.brokers.MeasurementMessageBroker;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MeasurementService measurementService;


    @Value("device_queue")
    String deviceQueueName;

    @Value("measurement_queue")
    String measurementQueueName;

    @Value("${spring.rabbitmq.host}")
    String host;

    @Value("${spring.rabbitmq.port}")
    int port;

    @Value("${spring.rabbitmq.username}")
    String username;

    @Value("${spring.rabbitmq.password}")
    String password;

    @Value("${spring.rabbitmq.virtual-host}")
    String virtualHost;

    @Bean
    Queue deviceQueue() {
        return new Queue(deviceQueueName, false);
    }

    @Bean
    Queue measurementQueue() {
        return new Queue(measurementQueueName, false);
    }

    @Bean
    MessageListenerContainer deviceMessageListenerContainer(ConnectionFactory connectionFactory ) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(deviceQueue());
        simpleMessageListenerContainer.setMessageListener(new DeviceMessageBroker(deviceService));
        return simpleMessageListenerContainer;
    }

    @Bean
    MessageListenerContainer measurementMessageListenerContainer(ConnectionFactory connectionFactory ) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(measurementQueue());
        simpleMessageListenerContainer.setMessageListener(new MeasurementMessageBroker(measurementService));
        return simpleMessageListenerContainer;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }


}


