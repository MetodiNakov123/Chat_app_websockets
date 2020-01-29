package com.example.websockets.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    // websocket endpoint so koj klientite ke se konektiraat so websocket serverot
    //SockJS ovozmozuva fallback opcii za browsers koi nepodrzuvaat websocket
    //STOMP -> Simple Text Oriented   Messaging Protocol - go definira formatot i pravilata za razmena na podatoci
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }



    //konfigurira messageBroker koj se koristi za rutiranje na poraki od eden klient do drug/i
    // Producer - Message Broker se sostoi od: , Exchanges(fanout,direct,topic,headers) - binding - , Queues, -- Consumer
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        //definira deka porakite cija destinacija pocnuva so /app ke bidat rutirani kon message-handling metodi


        //registry.enableSimpleBroker("/topic");   // Enables a simple in-memory broker



        //   Use this for enabling a Full featured broker like RabbitMQ
        // porakite cija destinacija pocnuva so /topic ke bidat rutirani na message broker koi broadcast ke gi isprati
        // do site klienti koi se subscribed na odreden topic
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                //.setRelayPort(15672)
                .setClientLogin("guest")
                .setClientPasscode("guest");

    }
}
