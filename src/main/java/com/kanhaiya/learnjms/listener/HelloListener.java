package com.kanhaiya.learnjms.listener;

import com.kanhaiya.learnjms.config.JmsConfig;
import com.kanhaiya.learnjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_ACTIVE_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders header, Message message){

        System.out.println("A message is received");
        System.out.println(helloWorldMessage);

        //throw new RuntimeException("kboom");
    }

    /*@JmsListener(destination = JmsConfig.MY_ACTIVE_SEND_RCV_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders header, Message message) throws JMSException {

        System.out.println("received msg : ");
        System.out.println(helloWorldMessage.getMessage());

        HelloWorldMessage returnMsg = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), returnMsg);
    }*/
}
