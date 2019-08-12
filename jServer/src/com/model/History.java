/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;

/**
 *
 * @author chauc
 */
public class History implements Serializable{
    private String sender;
    private String mess;
    private String to;
    private String time;

    public History() {
    }

    public History(String sender, String mess, String to, String time) {
        this.sender = sender;
        this.mess = mess;
        this.to = to;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
