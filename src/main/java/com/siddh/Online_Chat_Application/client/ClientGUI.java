package com.siddh.Online_Chat_Application.client;

import com.siddh.Online_Chat_Application.Message;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ClientGUI  extends JFrame implements MessageListener{


//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        MyStompClient myStompClient=new MyStompClient("Siddharth");
//
//        myStompClient.sendMessage(new Message("Siddharth","hello world"));
//        myStompClient.disconnectUser("Siddharth");
//    }

    private JPanel connectedUsersPanel;
    private JPanel messagePanel;
    private MyStompClient myStompClient;
    private String username;
    private JScrollPane messagePanelScrollPane;

    public ClientGUI(String username) throws ExecutionException, InterruptedException {
        super("User: "+username);

        this.username=username;
        myStompClient=new MyStompClient(this,username);

        setSize(800,500);
        setLocationRelativeTo(null);

        //since default operation is hide on close
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option=JOptionPane.showConfirmDialog(ClientGUI.this,"Do you really want to leave this Application?","Confirm it",JOptionPane.YES_NO_OPTION);

                if(option==JOptionPane.YES_OPTION){
                    myStompClient.disconnectUser(username);
                    ClientGUI.this.dispose();
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateMessageSize();
            }
        });


        getContentPane().setBackground(Utilities.PRIMARY_COLOR);

        addGuiComponents();


    }
    private void addGuiComponents(){
        addConnectedUserComponents();
        addChatComponents();
    }

    private void addConnectedUserComponents(){
        //normally JPanel flows 'flow layout'
        connectedUsersPanel=new JPanel();
        connectedUsersPanel.setBorder(Utilities.addPadding(8,8,8,8));
        connectedUsersPanel.setLayout(new BoxLayout(connectedUsersPanel,BoxLayout.Y_AXIS));
        connectedUsersPanel.setBackground(Utilities.SECONDARY_COLOR);
        connectedUsersPanel.setPreferredSize(new Dimension(200,getHeight()));

        JLabel connectedUsersLabel=new JLabel("Connected Users");
        connectedUsersLabel.setFont(new Font("Inter",Font.BOLD,16));
        connectedUsersLabel.setForeground(Utilities.TEXT_COLOR);
        connectedUsersPanel.add(connectedUsersLabel);

        add(connectedUsersPanel,BorderLayout.WEST);
    }

    private void addChatComponents(){
        JPanel chatPanel=new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.setBackground(Utilities.TRANSPARENT_COLOR);

        messagePanel=new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel,BoxLayout.Y_AXIS));
        messagePanel.setBackground(Utilities.TRANSPARENT_COLOR);

        messagePanelScrollPane=new JScrollPane(messagePanel);
        messagePanelScrollPane.setBackground(Utilities.TRANSPARENT_COLOR);
        messagePanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messagePanelScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        //revalidate everytime we scroll
        messagePanelScrollPane.getViewport().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                revalidate();
                repaint();
            }
        });

        chatPanel.add(messagePanelScrollPane,BorderLayout.CENTER);

//        JLabel message=new JLabel("Random Message");
//        message.setFont(new Font("Inter",Font.BOLD,16));
//        message.setForeground(Utilities.TEXT_COLOR);
//        messagePanel.add(message);

//        messagePanel.add(createChatMessageComponent(new Message("Siddharth","Hello World")));

        JPanel inputPanel=new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Utilities.TRANSPARENT_COLOR);
        inputPanel.setBorder(Utilities.addPadding(8,8,8,8));

        JTextField inputField=new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                //take input only when input field is focused
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    String input = inputField.getText();

                    // empty message (prevent empty messages)
                    if(input.isEmpty()) return;

                    inputField.setText("");

//                    messagePanel.add(createChatMessageComponent(new Message("Siddharth",input)));
//                    //refresh the gui to show the message
//                    repaint();
//                    revalidate();

                    myStompClient.sendMessage(new Message(username,input));
                }
            }
        });
        inputField.setForeground(Utilities.TEXT_COLOR);
        inputField.setBackground(Utilities.SECONDARY_COLOR);
        inputField.setBorder(Utilities.addPadding(0,8,0,8));
        inputField.setFont(new Font("INTER",Font.PLAIN,14));
        inputField.setPreferredSize(new Dimension(inputPanel.getWidth(),40));

        inputPanel.add(inputField,BorderLayout.CENTER);
        chatPanel.add(inputPanel,BorderLayout.SOUTH);


        add(chatPanel,BorderLayout.CENTER);
    }

    private JPanel createChatMessageComponent(Message message){
        JPanel chatMessage=new JPanel();
        chatMessage.setBackground(Utilities.TRANSPARENT_COLOR);
        chatMessage.setLayout(new BoxLayout(chatMessage,BoxLayout.Y_AXIS));
        chatMessage.setBorder(Utilities.addPadding(15,15,5,15));

        JLabel usernameLabel=new JLabel(message.getUser());
        usernameLabel.setFont(new Font("Inter", Font.BOLD, 15));
        usernameLabel.setForeground(Utilities.TEXT_COLOR);
        chatMessage.add(usernameLabel);

        JLabel messageLabel=new JLabel();
        //using it for wrapping long messages
        messageLabel.setText("<html>"+
                "<body style='width:"+(0.40*getWidth())+"'px>"+
                message.getMessage()
                +"</body>"
                +"</html>");
        messageLabel.setFont(new Font("Inter", Font.PLAIN, 14));
        messageLabel.setForeground(Utilities.TEXT_COLOR);
        chatMessage.add(messageLabel);

        return chatMessage;
    }

    @Override
    public void onMessageRecieve(Message message) {
//        System.out.println("On Message Recieve");
        messagePanel.add(createChatMessageComponent(message));
        revalidate();
        repaint();

        messagePanelScrollPane.getVerticalScrollBar().setValue(Integer.MAX_VALUE);
    }

    @Override
    public void onActiveUsersUpdated(ArrayList<String> users) {
        //remove the current user list panel (which should be the second component in the panel)
        //the user list panel doesn't get added until after and this is mainly for when the users get updated
        if(connectedUsersPanel.getComponents().length>=2){
            connectedUsersPanel.remove(1);
        }

        JPanel userListPanel=new JPanel();
        userListPanel.setBackground(Utilities.TRANSPARENT_COLOR);
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));

        for(String user : users){
            JLabel username=new JLabel();
            username.setText(user);
            username.setForeground(Utilities.TEXT_COLOR);
            username.setFont(new Font("Inter", Font.BOLD, 14));
            userListPanel.add(username);
        }

        connectedUsersPanel.add(userListPanel);
        revalidate();
        repaint();
    }

    private void updateMessageSize(){
        for(int i=0;i<messagePanel.getComponents().length;i++){
            Component component=messagePanel.getComponent(i);
            if(component instanceof JPanel){
                JPanel chatMessage=(JPanel) component;

                if(chatMessage.getComponent(1) instanceof JLabel){
                    JLabel messageLabel=(JLabel) chatMessage.getComponent(1);
                    messageLabel.setText("<html>"+
                            "<body style='width:"+(0.40*getWidth())+"'px>"+
                            messageLabel.getText()
                            +"</body>"
                            +"</html>");
                }
            }

        }
    }
}
