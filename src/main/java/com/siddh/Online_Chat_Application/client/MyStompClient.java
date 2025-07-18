package com.siddh.Online_Chat_Application.client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

public class MyStompClient {
    private StompSession session;
    private String username;

    public MyStompClient(String username){
        this.username=username;

        List<Transport> transports=new ArrayList<>();
        transports.add(new WebSocketTransport((new StandardWebSocketClient())));
        SockJsClient sockJsClient=new SockJsClient(transports);

        //sockJsClient doesn't support itself the stomp protocol
        WebSocketStompClient stompClient=new WebSocketStompClient(sockJsClient);

        //converting to json
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler=new MyStompSessionHandler(username);
        String url="ws://localhost:8081/ws";



    }
}
