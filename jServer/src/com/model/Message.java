package com.model;

import com.model.userr;
import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    public String type, sender, content, recipient;
    public userr user;
    public ArrayList<History> listHistory;
    public ArrayList<tableGaming> listTable;
    public ArrayList<userr> listRank;

    public Message(String type, ArrayList<userr> listRank) {
        this.type = type;
        this.listRank = listRank;
        this.content = content;
        this.recipient = recipient;
    }

    public Message(String type, String content, String recipient, ArrayList<tableGaming> listTable) {
        this.type = type;
        this.listTable = listTable;
        this.content = content;
        this.recipient = recipient;
    }

    public Message(String type, String sender, String content, String recipient) {
        this.type = type;
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
    }

    public Message(String type, String sender, String content, String recipient, ArrayList<History> listHistory ) {
        this.listHistory = listHistory;
        this.type = type;
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
    }

    public Message(String type, String content, String recipient, userr user) {
        this.type = type;
        this.content = content;
        this.recipient = recipient;
        this.user = user;
    }

    public Message(String type, String sender, String content, String recipient, userr user) {
        this.type = type;
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
        this.user = user;
    }

   
    @Override
    public String toString() {
        return "{type='" + type + "', sender='" + sender + "', content='" + content + "', recipient='" + recipient + "'}";
    }
}
