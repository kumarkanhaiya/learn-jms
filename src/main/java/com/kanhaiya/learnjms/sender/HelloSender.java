package com.kanhaiya.learnjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanhaiya.learnjms.config.JmsConfig;
import com.kanhaiya.learnjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    /*@Scheduled(fixedRate = 2000)
    public void sendMessage(){
        System.out.println("I am sending a message.");

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_ACTIVE_QUEUE, message);

        System.out.println("Message Sent");
    }*/

    @Scheduled(fixedRate = 5000)
    public void sendRcvMessage() throws JMSException {

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_ACTIVE_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                try {
                    Message helloMsg = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMsg.setStringProperty("_type", "com.kanhaiya.learnjms.model.HelloWorldMessage");

                    System.out.println("Sending Hello");

                    return helloMsg;
                } catch (JsonProcessingException e) {
                    throw new JMSException("kboom");
                }
            }
        });

        System.out.println("returned msg : " +receivedMsg.getBody(String.class));
    }
}
