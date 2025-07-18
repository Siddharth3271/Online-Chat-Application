package com.siddh.Online_Chat_Application;
import com.siddh.Online_Chat_Application.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

//handle the websocket request
@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    //allow to automatically inject the dependencies when creating an instance of the websocket controller
    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate=messagingTemplate;
    }

    @MessageMapping("/message")
    public void handleMessage(Message message){
        //debug
        System.out.println("Received Message from user: "+message.getUser()+": "+message.getMessage());

        messagingTemplate.convertAndSend("/topic/messages",message);

        //debug
        System.out.println("Sent Message to /topic/messages: "+message.getUser()+": "+message.getMessage());
    }
}
