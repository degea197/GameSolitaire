package com.socket;

import com.model.History;
import com.model.Message;
import com.model.tableGaming;
import com.model.userr;
import com.view.ServerFrame;
import java.io.*;
import java.net.*;
import java.sql.DriverManager;
import com.mysql.jdbc.Connection;
import static com.oracle.webservices.internal.api.databinding.DatabindingModeFeature.ID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

class ServerThread extends Thread {

    public SocketServer server = null;
    public Socket socket = null;
    public int ID = -1;
    public String username = "";
    public ObjectInputStream streamIn = null;
    public ObjectOutputStream streamOut = null;
    public ServerFrame ui;
    public userr user;

    public ServerThread(SocketServer _server, Socket _socket) {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getPort();
        ui = _server.ui;
    }

    public ServerThread(SocketServer _server, Socket _socket, userr user) {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getPort();
        ui = _server.ui;
        this.user = user;
    }

    public void send(Message msg) {
        try {
            streamOut.writeObject(msg);
            streamOut.flush();
        } catch (IOException ex) {
            System.out.println("Exception [SocketClient : send(...)]");
        }
    }

    public int getID() {
        return ID;
    }

    @SuppressWarnings("deprecation")
    public void run() {
        ui.jTextArea1.append("\nServer Thread " + ID + " running.");
        while (true) {
            try {
                System.out.println("lon");
                Message msg = (Message) streamIn.readObject();
                System.out.println(msg.sender);
                System.out.println(msg.content);
                System.out.println(msg.recipient);
                System.out.println(msg.type);
//                if (msg.type.equals("login")) {
//                    user = msg.user;
//                    System.out.println(user.getName());
//                    System.out.println(user.getPass());
//                }
                server.handle(ID, msg);
            } catch (Exception ioe) {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);
                stop();
            }
        }
    }

    public void open() throws IOException {
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamOut.flush();
        streamIn = new ObjectInputStream(socket.getInputStream());
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
        if (streamOut != null) {
            streamOut.close();
        }
    }
}

public class SocketServer implements Runnable {

    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread thread = null;
    public int clientCount = 0, port = 13000;
    public ServerFrame ui;
    private Connection con;
    private ArrayList<String> listInvite;

    public SocketServer(ServerFrame frame) {
        listInvite = new ArrayList<String>();
        clients = new ServerThread[50];
        ui = frame;
        try {
            server = new ServerSocket(port);
            getDBconnection("chatroom", "root", "");
            port = server.getLocalPort();
            ui.jTextArea1.append("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
            start();
        } catch (IOException ioe) {
            ui.jTextArea1.append("Can not bind to port : " + port + "\nRetrying");
            ui.RetryStart(0);
        }
    }

    public SocketServer(ServerFrame frame, int Port) {
        clients = new ServerThread[50];
        ui = frame;
        port = Port;
        getDBconnection("chatroom", "root", "");
        try {
            server = new ServerSocket(port);
            port = server.getLocalPort();
            ui.jTextArea1.append("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
            start();
        } catch (IOException ioe) {
            ui.jTextArea1.append("\nCan not bind to port " + port + ": " + ioe.getMessage());
        }
    }

    private void getDBconnection(String dbName, String username, String password) {
        String dbUrl = "jdbc:mysql://localhost/" + dbName;
        System.out.println(dbName);
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = (Connection) DriverManager.getConnection(dbUrl,
                    username, password);
        } catch (Exception e) {
            ui.jTextArea1.append("don't connection\n");
        }
        ui.jTextArea1.append("connected DB\n");
    }

    public void run() {
        while (thread != null) {
            try {
                ui.jTextArea1.append("\nWaiting for a client ...");
                addThread(server.accept());
            } catch (Exception ioe) {
                ui.jTextArea1.append("\nServer accept error: \n");
                ui.RetryStart(0);
            }
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
    }

    private int findClient(int ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    public synchronized void handle(int ID, Message msg) throws Exception {
        if (msg.content.equals(".bye")) {
            resetTable();
            Announce("signout", "SERVER", msg.sender);
            remove(ID);
        } else if (msg.type.equals("login")) {
            if (findUserThread(msg.sender) == null) {
                clients[findClient(ID)].user = msg.user;
                if (checkUser(msg.user, ID)) {
                    ArrayList<userr> listFr = checkFriend(clients[findClient(ID)]);
                    clients[findClient(ID)].user.setListFriend(listFr);
                    clients[findClient(ID)].username = msg.sender;
                    clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", msg.sender, clients[findClient(ID)].user));
                    Announce("newuser", "SERVER", msg.sender, msg.user);
                    SendUserList(msg.sender, msg.user);
                } else {
                    clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender, msg.user));
                }
            } else {
                clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender, msg.user));
            }
        } else if (msg.type.equals("message")) {
            if (msg.recipient.equals("All")) {
                Announce("message", msg.sender, msg.content);
            } else {
                findUserThread(msg.recipient).send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                clients[findClient(ID)].send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                saveToHistory(msg.sender, msg.content, msg.recipient);
            }
        } else if (msg.type.equals("message1")) {
            if (msg.recipient.equals("All")) {
                Announce("message1", msg.sender, msg.content);
            } else {
                saveToHistory(msg.sender, msg.content, msg.recipient);
                findUserThread(msg.recipient).send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                clients[findClient(ID)].send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
            }
        } else if (msg.type.equals("messageChatRoom")) {
            if (msg.recipient.equals("All")) {
                Announce("messageChatRoom", msg.sender, msg.content);
            } else {
                saveToHistory(msg.sender, msg.content, msg.recipient);
                findUserThread(msg.recipient).send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                clients[findClient(ID)].send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
            }
        } else if (msg.type.equals("test")) {
            clients[findClient(ID)].send(new Message("test", "SERVER", "OK", msg.sender));
        } 
        else if (msg.type.equals("upload_req")) {
            if (msg.recipient.equals("All")) {
                clients[findClient(ID)].send(new Message("message", "SERVER", "Uploading to 'All' forbidden", msg.sender));
            } else {
                findUserThread(msg.recipient).send(new Message("upload_req", msg.sender, msg.content, msg.recipient));
            }
        } else if (msg.type.equals("upload_res")) {
            if (!msg.content.equals("NO")) {
                String IP = findUserThread(msg.sender).socket.getInetAddress().getHostAddress();
                findUserThread(msg.recipient).send(new Message("upload_res", IP, msg.content, msg.recipient));
            } else {
                findUserThread(msg.recipient).send(new Message("upload_res", msg.sender, msg.content, msg.recipient));
            }
        } else if (msg.type.equals("invite_req")) {
            System.out.println("invite_req");
            updatetableGaming1(msg.content, msg.recipient);
            findUserThread(msg.recipient).send(new Message("invite_req", msg.sender, msg.content, msg.recipient, findUserThread(msg.recipient).user));
        } else if (msg.type.equals("invite_res")) {
            if (!msg.content.equals("NO")) {

                boolean c1 = false, c2 = false;
                for (String s : listInvite) {
                    if (s.equals(msg.recipient)) {
                        c1 = true;
                    }
                    if (s.equals(msg.sender)) {
                        c2 = true;
                    }
                }
                if (!c1) {
                    listInvite.add(msg.recipient);
                }
                if (!c2) {
                    listInvite.add(msg.sender);
                }
//                findUserThread(msg.recipient).send(new Message("invite_res", msg.sender, msg.content, msg.recipient, findUserThread(msg.sender).user));
                findUserThread(msg.sender).send(new Message("invite_res", msg.sender, msg.content, msg.recipient, findUserThread(msg.recipient).user));
                for (String s : listInvite) {
                    System.out.println("==================");
                    System.out.println(s);
                    System.out.println("==================");
                    if (!s.equals(msg.sender)) {
                        findUserThread(s).send(new Message("invite_res", msg.sender, msg.content, s, findUserThread(msg.sender).user));
                    }
                    if (!s.equals(msg.sender) && !s.equals(msg.recipient)) {
                        findUserThread(msg.sender).send(new Message("invite_res", msg.sender, msg.content, s, findUserThread(s).user));
                    }
                }

            } else {
                findUserThread(msg.recipient).send(new Message("invite_res", msg.sender, msg.content, msg.recipient));
            }
        } else if (msg.type.equals("chatWithMyFriend")) {
            clients[findClient(ID)].send(new Message("chatWithMyFriend", "SERVER", "TRUE", msg.sender, clients[findClient(ID)].user));
//            System.out.println("chatWithMyFriend");
//            System.out.println(clients[findClient(ID)].user.getId());
//            String query = "Select * FROM friend WHERE idUser ='" + clients[findClient(ID)].user.getId() + "'";
//            try {
//                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery(query);
//                while (rs.next()) {
//                    int id = rs.getInt("idFriend");
//                    SendUserList(id);
//                    System.out.println(id);
//                    for (int i = 0; i < clientCount; i++) {
//                        if (clients[i].user.getId() == id) {
//                            System.out.println(clients[i].user.getName());
//                        }
//                    }

//                }
//                SendUserList(msg.sender, msg.user,"chatWithMyFriend");
////                clients[findClient(ID)].send(new Message("chatWithMyFriend", "SERVER", "OK", msg.sender));
//            } catch (Exception e) {
//                System.out.println("dcm");
//            }
        } else if (msg.type.equals("showHistory")) {
            System.out.println("showHistory");
//            clients[findClient(ID)].user.setListHis(showHistory(msg.sender, msg.recipient));
            ArrayList<History> listH = showHistory(msg.sender, msg.recipient);
            for (History lis : listH) {
                System.out.println("++++++++++++++++++");
                System.out.println(lis.getSender());
                System.out.println(lis.getMess());
                System.out.println(lis.getTo());
                System.out.println(lis.getTime());
                System.out.println("++++++++++++++++++");
            }
            System.out.println(clients[findClient(ID)].user.getListHis().size());
//            clients[findClient(ID)].send(new Message("showHistory", "SERVER", msg.content, msg.sender, clients[findClient(ID)].user));
            clients[findClient(ID)].send(new Message("showHistory", "SERVER", msg.content, msg.sender, listH));
        } else if (msg.type.equals("NewGame")) {
            ArrayList<tableGaming> listTable = showlistTable(msg.sender);
            clients[findClient(ID)].send(new Message("NewGame", msg.content, msg.sender, listTable));

        } else if (msg.type.equals("playGames")) {
            updatetableGaming(msg.content, msg.sender);
            clients[findClient(ID)].send(new Message("playGames", "SERVER", msg.content, msg.sender, clients[findClient(ID)].user));
        } else if (msg.type.equals("winner")) {
            System.out.println("aaaaaaaaaaaaaaaaaa");
            findUserThread(msg.content).send(new Message("looser", msg.sender, msg.content, msg.recipient));
            updatePoint(msg.sender, msg.content);
            saveHistory(msg.sender);
//            clients[findClient(ID)].send(new Message("winner", "SERVER", "TRUE", msg.sender, clients[findClient(ID)].user));

        } else if (msg.type.endsWith("rankPoint")) {
            ArrayList<userr> listRank = sortByPoint();
            clients[findClient(ID)].send(new Message("rankPoint", listRank));
//            clients[findClient(ID)].send(new Message("showHistory", "SERVER", msg.content, msg.sender, listRank));

        }

    }

    private boolean checkUser(userr user, int ID) throws Exception {
//        String query = "Select * FROM userr WHERE name ='" + user.getName() + "' AND pass ='" + user.getPass() + "'";
        String query = "Select * FROM userr WHERE userName ='" + user.getName() + "' AND password ='" + user.getPass() + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
//                ArrayList<userr> listU = new ArrayList<userr>();
                int id = rs.getInt("id");
//                System.out.println(id);
                clients[findClient(ID)].user.setId(id);
//                String query2 = "SELECT userr.id,userName,password FROM userr INNER JOIN friend WHERE friend.idFriend = userr.id and friend.idUser = '" + id + "'";
//                Statement stmt2 = con.createStatement();
//                ResultSet rs2 = stmt2.executeQuery(query2);
//                while(rs2.next()){
//                    userr friend = new userr(rs2.getInt("userr.id"), rs2.getString("userName"), rs2.getString("password"));
//                    listU.add(friend);
//                    System.out.println("===================");
//                    System.out.println(friend.getId());
//                    System.out.println(friend.getName());
//                    System.out.println(friend.getPass());
//                    System.out.println("===================");
//                }
//                clients[findClient(ID)].user.setListFriend(listU);
                return true;
            }
        } catch (Exception e) {
            System.out.println("cccc");
            throw e;
        }
        return false;
    }

    public void Announce(String type, String sender, String content) {
        Message msg = new Message(type, sender, content, "All");
        for (int i = 0; i < clientCount; i++) {
            clients[i].send(msg);
        }
    }

    public void Announce(String type, String sender, String content, userr user) {
        Message msg = new Message(type, sender, content, "All", user);
        for (int i = 0; i < clientCount; i++) {
            clients[i].send(msg);
        }
    }

    public void SendUserList(String toWhom) {
        for (int i = 0; i < clientCount; i++) {
            findUserThread(toWhom).send(new Message("newuser", "SERVER", clients[i].username, toWhom));
        }
    }

    public void SendUserList(int id) {
        for (int i = 0; i < clientCount; i++) {
//            String cc = String.valueOf(id);
            findUserThread(id).send(new Message("chatWithMyFriend", "SERVER", String.valueOf(id), clients[i].username, clients[i].user));
        }
    }

    public void SendUserList(String toWhom, userr user) {
        for (int i = 0; i < clientCount; i++) {
            findUserThread(toWhom).send(new Message("newuser", "SERVER", clients[i].username, toWhom, user));
        }
    }

    public void SendUserList(String toWhom, userr user, String type) {
        for (int i = 0; i < clientCount; i++) {
            findUserThread(toWhom).send(new Message(type, "SERVER", clients[i].username, toWhom, user));
        }
    }

    public ServerThread findUserThread(String usr) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].username.equals(usr)) {
                return clients[i];
            }
        }
        return null;
    }

    public ServerThread findUserThread(int id) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].user.getId() == id) {
                return clients[i];
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public synchronized void remove(int ID) {
        int pos = findClient(ID);
        if (pos >= 0) {
            ServerThread toTerminate = clients[pos];
            ui.jTextArea1.append("\nRemoving client thread " + ID + " at " + pos);
            if (pos < clientCount - 1) {
                for (int i = pos + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try {
                toTerminate.close();
            } catch (IOException ioe) {
                ui.jTextArea1.append("\nError closing thread: " + ioe);
            }
            toTerminate.stop();
        }
    }

    private void addThread(Socket socket) {
        if (clientCount < clients.length) {
            ui.jTextArea1.append("\nClient accepted: " + socket);
            clients[clientCount] = new ServerThread(this, socket);
            try {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            } catch (IOException ioe) {
                ui.jTextArea1.append("\nError opening thread: " + ioe);
            }
        } else {
            ui.jTextArea1.append("\nClient refused: maximum " + clients.length + " reached.");
        }
    }

    private ArrayList<userr> checkFriend(ServerThread client) throws SQLException {
        ArrayList<userr> listFr = new ArrayList<userr>();
        String query2 = "SELECT userr.id,userName,password FROM userr INNER JOIN friend WHERE friend.idFriend = userr.id and friend.idUser = '" + client.user.getId() + "'";
        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery(query2);
        while (rs2.next()) {
            userr friend = new userr(rs2.getInt("userr.id"), rs2.getString("userName"), rs2.getString("password"));
            listFr.add(friend);
        }
        return listFr;
    }

    private void saveToHistory(String sender, String content, String recipient) throws SQLException {
        int idRelationship = 1;
        int id = new Random().nextInt(10000) + 1;
        int idUser = findUserThread(sender).user.getId();
        int idFriend = findUserThread(recipient).user.getId();
        String query = "select id from friend where idUser = '" + idUser + "' and idFriend = '" + idFriend + "'";
        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery(query);
        if (rs2.next()) {
            idRelationship = rs2.getInt("id");
        }
        String query2 = "insert into history(id,idRelationship,sender,mess,datChat,receiver) values ('" + id + "','" + idRelationship + "','" + sender + "','" + content + "',NOW(),'" + recipient + "')";
//        String query2 = " insert into history(id,idRelationship,sender,mess,receiver) values ('" +id+ "','"+idRelationship+"','"+sender+"','"+content+"','"+recipient+"')";
        Statement stm = con.createStatement();
        stm.executeUpdate(query2);
    }

    private ArrayList<History> showHistory(String sender, String recipient) throws SQLException {
        ArrayList<History> listHis = new ArrayList<History>();
        int id1 = findUserThread(sender).user.getId();
        int id2 = findUserThread(recipient).user.getId();
        System.out.println("------------------");
        System.out.println(id1 + "xxxxxxxxxx" + id2);
        System.out.println("------------------");
//        String query = "SELECT * FROM `history` WHERE idRelationship = " + id1 + " or idRelationship = " + id2 + " ORDER BY YEAR(datChat) , MONTH(datChat) , DAY(datChat) , HOUR(datChat) , MINUTE(datChat) , SECOND(datChat)";
        String query = "SELECT * FROM `history` WHERE idRelationship = " + id1 + " or idRelationship = " + id2 + " ORDER BY YEAR(datChat) , MONTH(datChat) , DAY(datChat) , HOUR(datChat) , MINUTE(datChat) , SECOND(datChat)";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            History his = new History(rs.getString("sender"), rs.getString("mess"), rs.getString("receiver"), rs.getString("datChat"));
            listHis.add(his);
        }
        for (History listHi : listHis) {
            System.out.println("==============");
            System.out.println(listHi.getSender());
            System.out.println(listHi.getMess());
            System.out.println(listHi.getTo());
            System.out.println(listHi.getTime());
            System.out.println("==============");
        }
        return listHis;
    }

    private ArrayList<tableGaming> showlistTable(String sender) throws SQLException {
        ArrayList<tableGaming> listTable = new ArrayList<tableGaming>();
        String query = "select * from tablegaming";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            tableGaming tb = new tableGaming(rs.getInt("id"), rs.getString("name"), rs.getInt("numberofPeople"), rs.getInt("idUser1"), rs.getInt("idUser2"));
            listTable.add(tb);
        }
        return listTable;
    }

    private void updatetableGaming(String content, String user) throws SQLException {
        String query1 = "select id from userr where userName ='" + user + "'";
        Statement stm1 = con.createStatement();
        ResultSet rs1 = stm1.executeQuery(query1);
        int id = 1;
        while (rs1.next()) {
            id = rs1.getInt("id");
        }
        String query = "update tablegaming set numberofPeople = 1, idUser1 = '" + id + "' where name = '" + content + "'";
        Statement stm = con.createStatement();
        int rs = stm.executeUpdate(query);
    }

    private void updatePoint(String sender, String content) throws SQLException {
        String query = "update userr set totalPoint = totalPoint + 1 where userName = '" + sender + "'";
        Statement stm = con.createStatement();
        int rs = stm.executeUpdate(query);

        String query1 = "update userr set totalPoint = totalPoint - 1 where userName = '" + content + "'";
        Statement stm1 = con.createStatement();
        int rs1 = stm1.executeUpdate(query1);
    }

    private ArrayList<userr> sortByPoint() throws SQLException {
        String query = "SELECT userName, totalPoint  FROM `userr` order by totalPoint DESC";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        ArrayList<userr> listRank = new ArrayList<userr>();
        while (rs.next()) {
            userr user = new userr(rs.getString("userName"), rs.getInt("totalPoint"));
            listRank.add(user);
        }
        return listRank;
    }

    private void updatetableGaming1(String content, String recipient) throws SQLException {
        String query1 = "select id from userr where userName ='" + recipient + "'";
        Statement stm1 = con.createStatement();
        ResultSet rs1 = stm1.executeQuery(query1);
        int id = 1;
        while (rs1.next()) {
            id = rs1.getInt("id");
        }
        String query = "update tablegaming set numberofPeople = 2, idUser2 = '" + id + "' where name = '" + content + "'";
        Statement stm = con.createStatement();
        int rs = stm.executeUpdate(query);
    }

    private void resetTable() throws SQLException {
        System.out.println("resetTable");
        String query1 = "update tablegaming set numberofPeople = 0, idUser2 = null, idUser1 = null";
        Statement stm1 = con.createStatement();
        int rs1 = stm1.executeUpdate(query1);
    }

    private void saveHistory(String sender) throws SQLException {
        int id = new Random().nextInt(10000) + 1;
        int idtable = new Random().nextInt(8) + 1;
        int idwinner = 1;
        String query1 = "select id from userr where userName = '" + sender + "'";
        Statement stm1 = con.createStatement();
        ResultSet rs = stm1.executeQuery(query1);
        if(rs.next()){
            idwinner = rs.getInt("id");
        }
        String query2 = "insert into winner(id,idtable,idwinner,timeWin) values ('" + id + "','" + idtable + "','" + idwinner + "',NOW())";
//        String query2 = " insert into history(id,idRelationship,sender,mess,receiver) values ('" +id+ "','"+idRelationship+"','"+sender+"','"+content+"','"+recipient+"')";
        Statement stm = con.createStatement();
        stm.executeUpdate(query2);
    }
}
