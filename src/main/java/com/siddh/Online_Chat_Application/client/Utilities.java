package com.siddh.Online_Chat_Application.client;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Utilities {
    public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);
    public static final Color PRIMARY_COLOR = Color.decode("#2F2D2D");
    public static final Color SECONDARY_COLOR = Color.decode("#484444");
    public static final Color TEXT_COLOR = Color.WHITE;


    //since swing and javafx doesn't support padding features and we have to do it manually
    public static EmptyBorder addPadding(int top, int left, int bottom, int right){
        return new EmptyBorder(top, left, bottom, right);
    }
}
