package com.siddh.Online_Chat_Application;

public class Message {
    //POJO: Plain Old Java Object

    private String user;
    private String message;

    public Message(){

    }

    public Message(String user,String message){
        this.user=user;
        this.message=message;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
