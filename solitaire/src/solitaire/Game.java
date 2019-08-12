package solitaire;

import com.socket.SocketClient;

public class Game {

    Engine game;
    public GUI gui;
    SocketClient client;
    private String userName;
    private String userName1;

    public SocketClient getClient() {
        return client;
    }

    public void setClient(SocketClient client) {
        this.client = client;
    }

    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName1() {
        return userName1;
    }

    public void setUserName1(String userName1) {
        this.userName1 = userName1;
    }


    public Game() {
        game = new Engine();
        gui = new GUI(game);
    }

    public Game(SocketClient client, String userName, String userName1) {
        this.client = client;
        this.userName = userName;
        this.userName1 = userName1;
        game = new Engine();
        gui = new GUI(game,client,userName,userName1);
    }

    
    
    public Game(SocketClient client, String userName) {
        this.client = client;
        this.userName = userName;
        game = new Engine();
        gui = new GUI(game,client,userName);
    }
    
    

    public static void main(String[] args) {
        Game Solitaire = new Game();
    }
}
