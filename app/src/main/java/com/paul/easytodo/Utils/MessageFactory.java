package com.paul.easytodo.Utils;

import android.os.Message;

public class MessageFactory {
    public static Message getMessage(int code){
        Message message=new Message();
        message.what=code;
        return message;
    }
}
