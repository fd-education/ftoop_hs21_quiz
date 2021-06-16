package ch.ffhs.quiz.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client{
    private final Socket client;
    private final OutputStream out;
    private final InputStream in;

    private String playerName;

    public Client(final String host, final int port) throws IOException{

        this.client = new Socket(host, port);

        this.out = client.getOutputStream();
        this.in = client.getInputStream();
    }

    public void closeConnection() throws IOException{
        if(!client.isClosed()) client.close();
    }

    public boolean isConnectionClosed(){
        return client.isClosed();
    }

    public InputStream getInputStream(){return in;}

    public OutputStream getOutputStream(){return out;}

    public void setPlayerName(final String name){
        this.playerName = name;
    }

    public String getPlayerName(){
        return playerName;
    }
}