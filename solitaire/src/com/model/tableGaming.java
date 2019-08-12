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
public class tableGaming implements Serializable {
    private int id;
    private String name;
    private int numberofPeople;
    private int idUser1;
    private int idUser2;

    public tableGaming() {
    }

    public tableGaming(String name) {
        this.name = name;
    }

    public tableGaming(int id, String name, int numberofPeople, int idUser1, int idUser2) {
        this.id = id;
        this.name = name;
        this.numberofPeople = numberofPeople;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
    }

    public tableGaming(String name, int idUser1) {
        this.name = name;
        this.idUser1 = idUser1;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberofPeople() {
        return numberofPeople;
    }

    public void setNumberofPeople(int numberofPeople) {
        this.numberofPeople = numberofPeople;
    }

    public int getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(int idUser1) {
        this.idUser1 = idUser1;
    }

    public int getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(int idUser2) {
        this.idUser2 = idUser2;
    }
    
}
