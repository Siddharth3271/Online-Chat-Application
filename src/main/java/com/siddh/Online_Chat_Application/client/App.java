package com.siddh.Online_Chat_Application.client;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) {
        //more thread safe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientGUI clientGUI= null;
                try {
                    clientGUI = new ClientGUI("Siddharth");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                clientGUI.setVisible(true);

            }
        });
    }
}
