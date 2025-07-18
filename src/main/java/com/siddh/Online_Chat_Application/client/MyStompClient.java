package com.siddh.Online_Chat_Application.client;

import com.siddh.Online_Chat_Application.Message;
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
import java.util.concurrent.ExecutionException;

public class MyStompClient {
    private StompSession session;
    private String username;

    public MyStompClient(MessageListener messageListener,String username) throws ExecutionException, InterruptedException {
        this.username=username;

        List<Transport> transports=new ArrayList<>();
        transports.add(new WebSocketTransport((new StandardWebSocketClient())));
        SockJsClient sockJsClient=new SockJsClient(transports);

        //sockJsClient doesn't support itself the stomp protocol
        WebSocketStompClient stompClient=new WebSocketStompClient(sockJsClient);

        //converting to json
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler=new MyStompSessionHandler(messageListener,username);
        String url="https://online-chat-application-hy1a.onrender.com/ws";

        session=stompClient.connectAsync(url,sessionHandler).get();

    }

    //send messages from client to server
    public void sendMessage(Message message) {
        try {
            session.send("/app/message", message);
            System.out.println("Message Sent: " + message.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectUser(String username){
        session.send("/app/disconnect",username);
        System.out.println("Disconnect User: "+username);
    }
}
