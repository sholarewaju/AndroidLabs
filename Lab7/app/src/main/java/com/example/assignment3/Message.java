package com.example.assignment3;

public class Message {
    Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", type=" + type +
                ", msg='" + msg + '\'' +
                '}';
    }
}
