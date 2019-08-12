package com.socket;

import com.model.Message;
import com.model.History;
import com.model.userr;
import com.view.Mainframe;
import com.view.InviteFrame;
import com.view.HistoryFrame;
import com.view.RankPoint;
import com.view.TableGamingFrame;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import solitaire.Game;

public class SocketClient implements Runnable {

    public int port;
    public String serverAddr;
    public Socket socket;
    public Mainframe ui;
    public ObjectInputStream In;
    public ObjectOutputStream Out;
    private ArrayList<userr> listUserLogin;

    public SocketClient(Mainframe frame) throws IOException {
        ui = frame;
        this.serverAddr = ui.serverAddr;
        this.port = ui.port;
        socket = new Socket(InetAddress.getByName(serverAddr), port);

        Out = new ObjectOutputStream(socket.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(socket.getInputStream());
        listUserLogin = new ArrayList<userr>();
    }

    public void run() {
        boolean keepRunning = true;
        while (keepRunning) {
            try {
                Message msg = (Message) In.readObject();
                System.out.println("Incoming : " + msg.toString());

                if (msg.type.equals("message")) {
                    if (msg.recipient.equals(ui.username)) {
                        ui.jtaPublicChat.append("[" + msg.sender + " > Me] : " + msg.content + "\n");
                    } else {
                        ui.jtaPublicChat.append("[" + msg.sender + " > " + msg.recipient + "] : " + msg.content + "\n");
                    }

                    if (!msg.content.equals(".bye") && !msg.sender.equals(ui.username)) {
                        String msgTime = (new Date()).toString();

                        try {
                            DefaultTableModel table = (DefaultTableModel) ui.historyFrame.jTable1.getModel();
                            table.addRow(new Object[]{msg.sender, msg.content, "Me", msgTime});
                        } catch (Exception ex) {
                        }
                    }
                } else if (msg.type.equals("message1")) {
                    if (msg.recipient.equals(ui.username)) {
                        ui.getjTextArea2().append("[" + msg.sender + " > Me] : " + msg.content + "\n");
                    } else {
                        ui.getjTextArea2().append("[" + msg.sender + " > " + msg.recipient + "] : " + msg.content + "\n");
                    }
                    if (!msg.content.equals(".bye") && !msg.sender.equals(ui.username)) {
                        String msgTime = (new Date()).toString();
                        try {
                            DefaultTableModel table = (DefaultTableModel) ui.historyFrame.jTable1.getModel();
                            table.addRow(new Object[]{msg.sender, msg.content, "Me", msgTime});
                        } catch (Exception ex) {
                        }
                    }
                } else if (msg.type.equals("messageChatRoom")) {
                    if (msg.recipient.equals(ui.username)) {
                        ui.cr.jTextArea1.append("[" + msg.sender + " > Me] : " + msg.content + "\n");
                    } else {
                        ui.cr.jTextArea1.append("[" + msg.sender + " > " + msg.recipient + "] : " + msg.content + "\n");
                    }
                    if (!msg.content.equals(".bye") && !msg.sender.equals(ui.username)) {
                        String msgTime = (new Date()).toString();
                        try {
                            DefaultTableModel table = (DefaultTableModel) ui.historyFrame.jTable1.getModel();
                            table.addRow(new Object[]{msg.sender, msg.content, "Me", msgTime});
                        } catch (Exception ex) {
                        }
                    }
                } else if (msg.type.equals("login")) {
                    if (msg.content.equals("TRUE")) {
                        ArrayList<userr> listF = msg.user.getListFriend();
                        for (userr object : listF) {
                            ui.model2.addElement(object.getName());
                            ui.getjComboBox1().addItem(object.getName());
                        }
                        ui.jbtLogin.setEnabled(false);
                        ui.jbtSendPublicChat.setEnabled(true);
                        ui.jbtPathFilePublicChat.setEnabled(true);
                        ui.jtaPublicChat.append("[SERVER > Me] : Login Successful\n");
                        ui.jtfUserName.setEnabled(false);
                        ui.jtfPassword.setEnabled(false);
                    } else {
                        ui.jtaPublicChat.append("[SERVER > Me] : Login Failed\n");
                    }
                } else if (msg.type.equals("test")) {
                    ui.jbtConnect.setEnabled(false);
                    ui.jbtLogin.setEnabled(true);
                    ui.jtfUserName.setEnabled(true);
                    ui.jtfPassword.setEnabled(true);
                    ui.jtfHostAddr.setEditable(false);
                    ui.jtfHostPort.setEditable(false);
//                    ui.jButton7.setEnabled(true);
                } else if (msg.type.equals("newuser")) {
                    if (!msg.content.equals(ui.username)) {
                        boolean exists = false;
                        for (int i = 0; i < ui.model.getSize(); i++) {
                            if (ui.model.getElementAt(i).equals(msg.content)) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
//                            userr user = msg.user;
//                            ui.getListUserLogin().add(user);
                            ui.model.addElement(msg.content);
                        }
                    }
                } else if (msg.type.equals("signout")) {
                    if (msg.content.equals(ui.username)) {
                        ui.jtaPublicChat.append("[" + msg.sender + " > Me] : Bye\n");
                        ui.jbtConnect.setEnabled(true);
                        ui.jbtSendPublicChat.setEnabled(false);
                        ui.jtfHostAddr.setEditable(true);
                        ui.jtfHostPort.setEditable(true);

                        for (int i = 1; i < ui.model.size(); i++) {
                            ui.model.removeElementAt(i);
                        }

                        ui.clientThread.stop();
                    } else {
                        ui.model.removeElement(msg.content);
                        ui.jtaPublicChat.append("[" + msg.sender + " > All] : " + msg.content + " has signed out\n");
                    }
                } else if (msg.type.equals("upload_req")) {

                    if (JOptionPane.showConfirmDialog(ui, ("Accept '" + msg.content + "' from " + msg.sender + " ?")) == 0) {
                        System.out.println("frame chau");
                        JFileChooser jf = new JFileChooser();
                        jf.setSelectedFile(new File(msg.content));
                        int returnVal = jf.showSaveDialog(ui);

                        String saveTo = jf.getSelectedFile().getPath();
                        if (saveTo != null && returnVal == JFileChooser.APPROVE_OPTION) {
                            Download dwn = new Download(saveTo, ui);
                            Thread t = new Thread(dwn);
                            t.start();
                            //send(new Message("upload_res", (""+InetAddress.getLocalHost().getHostAddress()), (""+dwn.port), msg.sender));
                            send(new Message("upload_res", ui.username, ("" + dwn.port), msg.sender));
                        } else {
                            send(new Message("upload_res", ui.username, "NO", msg.sender));
                        }
                    } else {
                        send(new Message("upload_res", ui.username, "NO", msg.sender));
                    }
                } else if (msg.type.equals("upload_res")) {
                    if (!msg.content.equals("NO")) {
                        int port = Integer.parseInt(msg.content);
                        String addr = msg.sender;

                        ui.jbtPathFilePublicChat.setEnabled(false);
                        ui.jbtSendFilePublicChat.setEnabled(false);
                        Upload upl = new Upload(addr, port, ui.file, ui);
                        Thread t = new Thread(upl);
                        t.start();
                    } else {
                        ui.jtaPublicChat.append("[SERVER > Me] : " + msg.sender + " rejected file request\n");
                    }
                } else if (msg.type.equals("invite_req")) {
                    if (JOptionPane.showConfirmDialog(ui, (msg.sender + " da moi ban choi game")) == 0) {
                        ui.cr = new InviteFrame(msg.user.getListFriend(), ui.client, ui.username);
                        ui.cr.setVisible(true);
                        ui.game = new Game(ui.client, ui.username, msg.sender);
                        System.out.println("invite_req==================");
                        System.out.println(ui.game.getClient().toString());
                        System.out.println(ui.game.getUserName());
                        System.out.println(ui.game.getUserName1());
                        System.out.println("invite_req==================");
                        send(new Message("invite_res", ui.username, "YES", msg.sender));
                    } else {
                        send(new Message("invite_res", ui.username, "NO", msg.sender));
                    }
                }  else if (msg.type.equals("invite_res")) {
                    if (!msg.content.equals("NO")) {
                        ui.cr.model.addElement(msg.user.getName());
//                        ui.cr = new InviteFrame();
//                        ui.cr.setVisible(true);
                    } else {
                        ui.jtaPublicChat.append("[SERVER > Me] : " + msg.sender + " rejected file request\n");
                    }
                } else if (msg.type.equals("chatWithMyFriend")) {
                    ui.cr = new InviteFrame(msg.user.getListFriend(), ui.client, ui.username);
                    ui.cr.setVisible(true);
                    ui.cr.setListUserLogin(listUserLogin);
                    System.out.println("=======================");
                    System.out.println(listUserLogin.size());
                    for (userr user : listUserLogin) {
                        System.out.println(user.getId());
                        System.out.println(user.getName());
                        System.out.println(user.getPass());
                    }
                    System.out.println("=======================");
                } else if (msg.type.equals("showHistory")) {
                    System.out.println("??????????");
                    System.out.println(msg.listHistory.size());
                    for (History object : msg.listHistory) {
                        System.out.println(object.getSender());
                        System.out.println(object.getMess());
                        System.out.println(object.getTo());
                        System.out.println(object.getTime());
                    }
                    ui.historyFrame = new HistoryFrame(msg.listHistory);
                    ui.historyFrame.setVisible(true);
                } else if (msg.type.equals("NewGame")) {
                    ui.tbf = new TableGamingFrame(msg.listTable, ui.client, ui.username);
                    ui.tbf.setVisible(true);
                } else if (msg.type.equals("playGames")) {
                    System.out.println("=========play game");
                    System.out.println(ui.client.toString());
                    System.out.println("=========");
                    ui.cr = new InviteFrame(msg.user.getListFriend(), ui.client, ui.username,msg.content,ui);
                    ui.cr.setVisible(true);
                    ui.game = new Game(ui.client, ui.username);
                } else if (msg.type.equals("winner")) {
                    if (JOptionPane.showConfirmDialog(ui, (" Ban da chien thang, ban co muon choi lai hay ko")) == 0) {
                        System.out.println("co");
//                        ui.cr = new InviteFrame(msg.user.getListFriend(), ui.client, ui.username);
//                        ui.cr.setVisible(true);
//                        ui.game = new Game();
//                        send(new Message("invite_res", ui.username, "YES", msg.sender));
                    } else {
//                        send(new Message("invite_res", ui.username, "NO", msg.sender));
                        System.out.println("ko");
                    }
                } else if (msg.type.equals("looser")) {
                    if (JOptionPane.showConfirmDialog(ui, (" Ban da chien thua, ban co muon choi lai hay ko")) == 0) {
                        System.out.println("co");
                        ui.game.gui.dispose();
                        ui.game = new Game(ui.client, msg.content, msg.sender);
//                        ui.cr = new InviteFrame(msg.user.getListFriend(), ui.client, ui.username);
//                        ui.cr.setVisible(true);
//                        ui.game = new Game();
//                        send(new Message("invite_res", ui.username, "YES", msg.sender));
                    } else {
//                        send(new Message("invite_res", ui.username, "NO", msg.sender));
                        System.out.println("ko");
                    }
                } else if (msg.type.equals("rankPoint")) {
                        System.out.println("cccccccccc");
                        ArrayList<userr> listrank = msg.listRank;
                        System.out.println(msg.listRank.size());
                        for (userr object : listrank) {
                            System.out.println("==================");
                            System.out.println(object.getName());
                            System.out.println(object.getPoint());
                            System.out.println("==================");
                        }
                        ui.rp = new RankPoint(msg.listRank);
//                        ui.rp = new RankPoint();
                        ui.rp.setVisible(true);
                    } 
                
                
                else {
                    ui.jtaPublicChat.append("[SERVER > Me] : Unknown message type\n");
                }
            } catch (Exception ex) {
                keepRunning = false;
                ui.jtaPublicChat.append("[Application > Me] : Connection Failure\n");
                ui.jbtConnect.setEnabled(true);
                ui.jtfHostAddr.setEditable(true);
                ui.jtfHostPort.setEditable(true);
                ui.jbtSendPublicChat.setEnabled(false);
                ui.jbtPathFilePublicChat.setEnabled(false);
                ui.jbtPathFilePublicChat.setEnabled(false);

                for (int i = 1; i < ui.model.size(); i++) {
                    ui.model.removeElementAt(i);
                }

                ui.clientThread.stop();

                System.out.println("Exception SocketClient run()");
                ex.printStackTrace();
            }
        }
    }

    public void send(Message msg) {
        try {
            Out.writeObject(msg);
            Out.flush();
            System.out.println("Outgoing : " + msg.toString());

            if (msg.type.equals("message") && !msg.content.equals(".bye")) {
                String msgTime = (new Date()).toString();
                try {
                    System.out.println("ghi lich su");
                    DefaultTableModel table = (DefaultTableModel) ui.historyFrame.jTable1.getModel();
                    table.addRow(new Object[]{"Me", msg.content, msg.recipient, msgTime});
                } catch (Exception ex) {
                }
            }
        } catch (IOException ex) {
            System.out.println("Exception SocketClient send()");
        }
    }

    public void closeThread(Thread t) {
        t = null;
    }
}
