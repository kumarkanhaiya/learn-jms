package com.kanhaiya.learnjms.listener;

import com.kanhaiya.learnjms.config.JmsConfig;
import com.kanhaiya.learnjms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class HelloListener {

    @JmsListener(destination = JmsConfig.MY_ACTIVE_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders header, Message message){

        System.out.println("A message is received");
        System.out.println(helloWorldMessage);
    }
}
