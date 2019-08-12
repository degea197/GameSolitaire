package com.view;

import com.model.userr;
import com.model.Message;
import com.socket.SocketClient;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import oracle.jrockit.jfr.JFR;
import solitaire.Game;

public class Mainframe extends javax.swing.JFrame implements ActionListener {

    public SocketClient client;
    public int port;
    public String serverAddr, username, password;
    public Thread clientThread;
    public DefaultListModel model;
    public DefaultListModel model2;
    public File file;
    public String historyFile = "D:/History.xml";
    public HistoryFrame historyFrame;
    public userr user;
    public InviteFrame cr;
    private ArrayList<userr> listUserLogin;
    public Game game;
    public TableGamingFrame tbf;
    public RankPoint rp;

    public JComboBox<String> getjComboBox1() {
        return jcbListHistory;
    }

    public void setjComboBox1(JComboBox<String> jComboBox1) {
        this.jcbListHistory = jComboBox1;
    }

    public JTextArea getjTextArea2() {
        return jtaChatWithMyFriends;
    }

    public void setjTextArea2(JTextArea jTextArea2) {
        this.jtaChatWithMyFriends = jTextArea2;
    }

    public ArrayList<userr> getListUserLogin() {
        return listUserLogin;
    }

    public void setListUserLogin(ArrayList<userr> listUserLogin) {
        this.listUserLogin = listUserLogin;
    }

    public Mainframe() {
        listUserLogin = new ArrayList<>();
        initComponents();
        jListChatWithMyFriends.setModel((model2 = new DefaultListModel()));
        model2.addElement("All");
        jListChatWithMyFriends.setSelectedIndex(0);
        this.setTitle("Chat Room");
        model.addElement("All");
        jlistPublicChat.setSelectedIndex(0);
//        jTextField6.setEditable(false);
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    client.send(new Message("message", username, ".bye", "SERVER"));
                    clientThread.stop();
                } catch (Exception ex) {
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

    }

    public boolean isWin32() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfHostAddr = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfHostPort = new javax.swing.JTextField();
        jbtConnect = new javax.swing.JButton();
        jtfUserName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfPassword = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaPublicChat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlistPublicChat = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        jtfMessPublicChat = new javax.swing.JTextField();
        jbtSendPublicChat = new javax.swing.JButton();
        jbtLogin = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jtfPathFilePublicChat = new javax.swing.JTextField();
        jbtPathFilePublicChat = new javax.swing.JButton();
        jbtSendFilePublicChat = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jbtShowHistory = new javax.swing.JButton();
        jbtNewChatRoom = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListChatWithMyFriends = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtaChatWithMyFriends = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jtftChatWithMyFriends = new javax.swing.JTextField();
        jbtSendMessChatWithMyFriends = new javax.swing.JButton();
        jbtSendFileWithMyFriends = new javax.swing.JButton();
        jbtPathFileChatWithMyFriends = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtfPathFileChatWithMyFriends = new javax.swing.JTextField();
        jcbListHistory = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Host Address : ");

        jtfHostAddr.setText("localhost");

        jLabel2.setText("Host Port : ");

        jtfHostPort.setText("13000");
        jtfHostPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfHostPortActionPerformed(evt);
            }
        });

        jbtConnect.setText("Connect");
        jbtConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConnectActionPerformed(evt);
            }
        });

        jtfUserName.setText("chau");
        jtfUserName.setEnabled(false);

        jLabel3.setText("Password :");

        jLabel4.setText("Username :");

        jtfPassword.setText("123");
        jtfPassword.setEnabled(false);

        jtaPublicChat.setColumns(20);
        jtaPublicChat.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        jtaPublicChat.setRows(5);
        jScrollPane1.setViewportView(jtaPublicChat);

        jlistPublicChat.setModel((model = new DefaultListModel()));
        jScrollPane2.setViewportView(jlistPublicChat);

        jLabel5.setText("Message : ");

        jbtSendPublicChat.setText("Send Message ");
        jbtSendPublicChat.setEnabled(false);
        jbtSendPublicChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSendPublicChatActionPerformed(evt);
            }
        });

        jbtLogin.setText("Login");
        jbtLogin.setEnabled(false);
        jbtLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtLoginActionPerformed(evt);
            }
        });

        jbtPathFilePublicChat.setText("...");
        jbtPathFilePublicChat.setEnabled(false);
        jbtPathFilePublicChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPathFilePublicChatActionPerformed(evt);
            }
        });

        jbtSendFilePublicChat.setText("Send");
        jbtSendFilePublicChat.setEnabled(false);
        jbtSendFilePublicChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSendFilePublicChatActionPerformed(evt);
            }
        });

        jLabel6.setText("File :");

        jLabel7.setText("    History:");

        jbtShowHistory.setText("Show");
        jbtShowHistory.setEnabled(false);
        jbtShowHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtShowHistoryActionPerformed(evt);
            }
        });

        jbtNewChatRoom.setText("new Room");
        jbtNewChatRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtNewChatRoomActionPerformed(evt);
            }
        });

        jLabel9.setText("Public Chat");

        jListChatWithMyFriends.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListChatWithMyFriendsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jListChatWithMyFriends);

        jtaChatWithMyFriends.setColumns(20);
        jtaChatWithMyFriends.setRows(5);
        jScrollPane4.setViewportView(jtaChatWithMyFriends);

        jLabel10.setText("Chat with my friends");

        jbtSendMessChatWithMyFriends.setText("Send Message ");
        jbtSendMessChatWithMyFriends.setEnabled(false);
        jbtSendMessChatWithMyFriends.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSendMessChatWithMyFriendsActionPerformed(evt);
            }
        });

        jbtSendFileWithMyFriends.setText("Send");
        jbtSendFileWithMyFriends.setEnabled(false);
        jbtSendFileWithMyFriends.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSendFileWithMyFriendsActionPerformed(evt);
            }
        });

        jbtPathFileChatWithMyFriends.setText("...");
        jbtPathFileChatWithMyFriends.setEnabled(false);
        jbtPathFileChatWithMyFriends.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPathFileChatWithMyFriendsActionPerformed(evt);
            }
        });

        jLabel11.setText("File :");

        jLabel12.setText("Message : ");

        jcbListHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbListHistoryMouseClicked(evt);
            }
        });

        jButton1.setText("New game");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel13.setText("New game");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtftChatWithMyFriends, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtSendMessChatWithMyFriends, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfPathFileChatWithMyFriends, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jbtPathFileChatWithMyFriends, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtSendFileWithMyFriends, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jcbListHistory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtShowHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtfHostAddr)
                            .addComponent(jtfUserName))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfHostPort)
                                    .addComponent(jtfPassword)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbtNewChatRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtConnect, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfMessPublicChat, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtSendPublicChat, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfPathFilePublicChat, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtPathFilePublicChat, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtSendFilePublicChat, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(83, 83, 83)
                                        .addComponent(jLabel9)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(69, 69, 69)))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfHostAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jtfHostPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtConnect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jtfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jbtShowHistory)
                    .addComponent(jcbListHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel13)
                    .addComponent(jbtNewChatRoom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jScrollPane3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtSendPublicChat)
                            .addComponent(jLabel5)
                            .addComponent(jtfMessPublicChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jbtSendFilePublicChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtPathFilePublicChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6)
                            .addComponent(jtfPathFilePublicChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtSendMessChatWithMyFriends)
                            .addComponent(jLabel12)
                            .addComponent(jtftChatWithMyFriends, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jbtSendFileWithMyFriends, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtPathFileChatWithMyFriends, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11)
                            .addComponent(jtfPathFileChatWithMyFriends, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConnectActionPerformed
        serverAddr = jtfHostAddr.getText();
        port = Integer.parseInt(jtfHostPort.getText());

        if (!serverAddr.isEmpty() && !jtfHostPort.getText().isEmpty()) {
            try {
                client = new SocketClient(this);
                clientThread = new Thread(client);
                clientThread.start();
                client.send(new Message("test", "testUser", "testContent", "SERVER"));
            } catch (Exception ex) {
                jtaPublicChat.append("[Application > Me] : Server not found\n");
            }
        }
    }//GEN-LAST:event_jbtConnectActionPerformed

    private void jbtLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtLoginActionPerformed
        jbtSendMessChatWithMyFriends.setEnabled(true);
        jbtShowHistory.setEnabled(true);
        jbtPathFileChatWithMyFriends.setEnabled(true);
        username = jtfUserName.getText();
        password = jtfPassword.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            user = new userr(username, password);
            client.send(new Message("login", username, password, "SERVER", user));
//            client.send(new Message("login", password, "SERVER", user));
        }
    }//GEN-LAST:event_jbtLoginActionPerformed

    private void jbtSendPublicChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSendPublicChatActionPerformed
        System.out.println("=============");
        System.out.println(client.toString());
        System.out.println("=============");
        String msg = jtfMessPublicChat.getText();
        String target = jlistPublicChat.getSelectedValue().toString();
        if (!msg.isEmpty() && !target.isEmpty()) {
            jtfMessPublicChat.setText("");
            client.send(new Message("message", username, msg, target));
        }
    }//GEN-LAST:event_jbtSendPublicChatActionPerformed

    private void jbtPathFilePublicChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPathFilePublicChatActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(this, "Select File");
        file = fileChooser.getSelectedFile();

        if (file != null) {
            if (!file.getName().isEmpty()) {
                jbtSendFilePublicChat.setEnabled(true);
                String str;

                if (jtfPathFilePublicChat.getText().length() > 30) {
                    String t = file.getPath();
                    str = t.substring(0, 20) + " [...] " + t.substring(t.length() - 20, t.length());
                } else {
                    str = file.getPath();
                }
                jtfPathFilePublicChat.setText(str);
            }
        }
    }//GEN-LAST:event_jbtPathFilePublicChatActionPerformed

    private void jbtSendFilePublicChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSendFilePublicChatActionPerformed
        long size = file.length();
        if (size < 120 * 1024 * 1024) {
            client.send(new Message("upload_req", username, file.getName(), jlistPublicChat.getSelectedValue().toString()));
        } else {
            jtaPublicChat.append("[Application > Me] : File is size too large\n");
        }
    }//GEN-LAST:event_jbtSendFilePublicChatActionPerformed

    private void jbtShowHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtShowHistoryActionPerformed
//        historyFrame = new HistoryFrame();
//        historyFrame.setLocation(this.getLocation());
//        historyFrame.setVisible(true);
        client.send(new Message("showHistory", username, password, jcbListHistory.getSelectedItem().toString()));
    }//GEN-LAST:event_jbtShowHistoryActionPerformed

    private void jbtNewChatRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtNewChatRoomActionPerformed
//        cr = new InviteFrame(listUserLogin);
//        cr.setVisible(true);
        client.send(new Message("chatWithMyFriend", username, password, "SERVER"));
    }//GEN-LAST:event_jbtNewChatRoomActionPerformed

    private void jbtSendMessChatWithMyFriendsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSendMessChatWithMyFriendsActionPerformed
        // TODO add your handling code here:
        String msg = jtftChatWithMyFriends.getText();
        String target = jListChatWithMyFriends.getSelectedValue().toString();
        System.out.println("target" + target);
        if (!msg.isEmpty() && !target.isEmpty()) {
            jtftChatWithMyFriends.setText("");
            client.send(new Message("message1", username, msg, target));
        }
    }//GEN-LAST:event_jbtSendMessChatWithMyFriendsActionPerformed

    private void jbtSendFileWithMyFriendsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSendFileWithMyFriendsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtSendFileWithMyFriendsActionPerformed

    private void jbtPathFileChatWithMyFriendsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPathFileChatWithMyFriendsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtPathFileChatWithMyFriendsActionPerformed

    private void jListChatWithMyFriendsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListChatWithMyFriendsMouseClicked
//        if(jList2.getSelectedValue().equals("cuong")){
//            jButton10.setEnabled(true);
//            jButton12.setEnabled(true);
//        }else{
//            jButton10.setEnabled(false);
//            jButton12.setEnabled(false);
//        }
        if (jListChatWithMyFriends.getSelectedValue().equals("all")) {
            jbtSendMessChatWithMyFriends.setEnabled(true);
        } else {
            jbtSendMessChatWithMyFriends.setEnabled(false);
            int[] a = new int[jListChatWithMyFriends.getModel().getSize()];
            Arrays.fill(a, 0);
            for (int i = 0; i < jListChatWithMyFriends.getModel().getSize(); i++) {
                for (int j = 0; j < jlistPublicChat.getModel().getSize(); j++) {
                    String s1 = String.valueOf(jListChatWithMyFriends.getModel().getElementAt(i));
                    String s2 = String.valueOf(jlistPublicChat.getModel().getElementAt(j));
                    if (s1.equals(s2)) {
//                    a[i] = 1;
                        if (jListChatWithMyFriends.getSelectedValue().equals(s1)) {
                            jbtSendMessChatWithMyFriends.setEnabled(true);
//                            jTextArea2.append(s1+" dang online ");
                        }
                    }
                }

            }
        }
//        for (int i = 0; i < jList2.getModel().getSize(); i++) {
//            if (a[i] == 1) {
//                jButton10.setEnabled(true);
//            } else {
//                jButton10.setEnabled(false);
//
//            }
//        }
    }//GEN-LAST:event_jListChatWithMyFriendsMouseClicked

    private void jcbListHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbListHistoryMouseClicked
        
    }//GEN-LAST:event_jcbListHistoryMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         client.send(new Message("NewGame", username, password, "SERVER"));
         System.out.println(client.toString());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtfHostPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfHostPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfHostPortActionPerformed

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Look & Feel exception");
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mainframe().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListChatWithMyFriends;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    public javax.swing.JButton jbtConnect;
    public javax.swing.JButton jbtLogin;
    private javax.swing.JButton jbtNewChatRoom;
    public javax.swing.JButton jbtPathFileChatWithMyFriends;
    public javax.swing.JButton jbtPathFilePublicChat;
    public javax.swing.JButton jbtSendFilePublicChat;
    public javax.swing.JButton jbtSendFileWithMyFriends;
    public javax.swing.JButton jbtSendMessChatWithMyFriends;
    public javax.swing.JButton jbtSendPublicChat;
    public javax.swing.JButton jbtShowHistory;
    private javax.swing.JComboBox<String> jcbListHistory;
    public javax.swing.JList jlistPublicChat;
    private javax.swing.JTextArea jtaChatWithMyFriends;
    public javax.swing.JTextArea jtaPublicChat;
    public javax.swing.JTextField jtfHostAddr;
    public javax.swing.JTextField jtfHostPort;
    public javax.swing.JTextField jtfMessPublicChat;
    public javax.swing.JPasswordField jtfPassword;
    public javax.swing.JTextField jtfPathFileChatWithMyFriends;
    public javax.swing.JTextField jtfPathFilePublicChat;
    public javax.swing.JTextField jtfUserName;
    public javax.swing.JTextField jtftChatWithMyFriends;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
