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

    public Client(String host, int port) throws IOException{

        this.client = new Socket(host, port);

        this.out = client.getOutputStream();
        this.in = client.getInputStream();
    }

    public void closeConnection() throws IOException{
        if(!client.isClosed()) client.close();
        in.close();
        out.close();
    }

    public InputStream getInput(){return in;}

    public OutputStream getOutput(){return out;}

    public void setPlayerName(String name){
        this.playerName = name;
    }

    public String getPlayerName(){
        return playerName;
    }
}