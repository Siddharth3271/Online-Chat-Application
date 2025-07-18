package com.siddh.Online_Chat_Application.client;

import com.siddh.Online_Chat_Application.Message;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

//managing connections
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private String username;
    private MessageListener messageListener;

    public MyStompSessionHandler(MessageListener messageListener,String username){
        this.username=username;
        this.messageListener=messageListener;
    }
    //after connecting with server
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Client Connected Successfully");
        session.send("/app/connect",username);

        session.subscribe("/topic/messages", new StompFrameHandler() {

            //tell the client the expected type of data sent to the server
            @Override
            public Type getPayloadType(StompHeaders headers) {

                System.out.println("Subscribing to topic/messages");
                return Message.class;       //convert incoming json data to message object
            }


            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    if(payload instanceof Message){
                        Message message=(Message) payload;    //store message object
                        messageListener.onMessageRecieve(message);
                        System.out.println("Received message: "+message.getUser()+": "+message.getMessage());
                    }
                    else{
                        System.out.println("Received Unexpected payload type: "+payload.getClass());
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Client subscribed to topic/messages");

        session.subscribe("/topic/users", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return new ArrayList<String>().getClass();
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try{
                    //from socket server to /topic/users
                    if(payload instanceof ArrayList){
                        ArrayList<String>activeUsers=(ArrayList<String>) payload;
                        messageListener.onActiveUsersUpdated(activeUsers);
                        System.out.println("Received Active Users"+activeUsers);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Subscribed to /topic/users");

        session.send("/app/request-users","");
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        exception.printStackTrace();
    }
}
