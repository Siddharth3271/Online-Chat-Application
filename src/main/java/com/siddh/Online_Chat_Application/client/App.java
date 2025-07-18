package com.siddh.Online_Chat_Application.client;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) {
        //more thread safe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String username=JOptionPane.showInputDialog(null,"Enter Username (Max: 16 characters)","Chat Application",JOptionPane.QUESTION_MESSAGE);

                if(username==null || username.isEmpty() || username.length()>16){
                    JOptionPane.showMessageDialog(null,"Invalid Username","Error!!!",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ClientGUI clientGUI= null;
                try {
                    clientGUI = new ClientGUI(username);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                clientGUI.setVisible(true);

            }
        });
    }
}
