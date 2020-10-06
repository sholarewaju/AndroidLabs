package com.example.assignment3;

public class Message {
    boolean type;
    String msg;

    public Message(boolean t, String message) {
        setMsg(message);
        setType(t);
    }

    public void setMsg(String message){
        msg = message;
    }

    public void setType(boolean t){
        type = t;
    }

    public String getMsg (){
        return msg;
    }

    public boolean getType(){
        return type;
    }
}
