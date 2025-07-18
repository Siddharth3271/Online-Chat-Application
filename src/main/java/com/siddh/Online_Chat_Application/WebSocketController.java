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
    private final WebSocketSessionManager sessionManager;

    //allow to automatically inject the dependencies when creating an instance of the websocket controller
    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate,WebSocketSessionManager sessionManager){
        this.messagingTemplate=messagingTemplate;
        this.sessionManager=sessionManager;
    }

    @MessageMapping("/message")
    public void handleMessage(Message message){
        //debug
        System.out.println("Received Message from user: "+message.getUser()+": "+message.getMessage());

        messagingTemplate.convertAndSend("/topic/messages",message);

        //debug
        System.out.println("Sent Message to /topic/messages: "+message.getUser()+": "+message.getMessage());
    }

    //track which users are online, updating the list and keep the UI in sync.
    @MessageMapping("/connect")
    public void connectUser(String username) {
        sessionManager.addUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println(username + " connected");
    }

    @MessageMapping("/disconnect")
    public void disconnectUser(String username) {
        sessionManager.removeUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println(username + " disconnected");
    }
}
