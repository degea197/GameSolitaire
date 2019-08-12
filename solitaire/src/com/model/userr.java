/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author chauc
 */
public class userr implements Serializable {

    private int id;
    private String name;
    private String pass;
    private ArrayList<userr> listFriend;
    private ArrayList<History> listHis;
    private int point;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public userr(String name, int point) {
        this.name = name;
        this.point = point;
    }

    
    
    public ArrayList<History> getListHis() {
        return listHis;
    }

    public void setListHis(ArrayList<History> listHis) {
        this.listHis = listHis;
    }

    public userr() {
    }

//    public userr(int id, String name, int totalPoint, String pass) {
//        this.id = id;
//        this.name = name;
//        this.totalPoint = totalPoint;
//        this.pass = pass;
//    }
    public userr(String name, String pass) {
        this.name = name;
        this.pass = pass;
        listFriend = new ArrayList<userr>();
        listHis = new ArrayList<History>();
    }

    public userr(int id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        listFriend = new ArrayList<userr>();
        listHis = new ArrayList<History>();
    }

    public ArrayList<userr> getListFriend() {
        return listFriend;
    }

    public void setListFriend(ArrayList<userr> listFriend) {
        this.listFriend = listFriend;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
