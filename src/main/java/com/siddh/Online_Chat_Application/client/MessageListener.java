
package com.siddh.Online_Chat_Application.client;

import com.siddh.Online_Chat_Application.Message;

import java.util.ArrayList;

public interface MessageListener {

    void onMessageRecieve(Message message);
    void onActiveUsersUpdated(ArrayList<String> users);
}
