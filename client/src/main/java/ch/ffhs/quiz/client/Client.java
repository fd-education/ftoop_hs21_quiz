package ch.ffhs.quiz.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * The Client Socket that is used to connect to the game server.
 */
public class Client{
    private final Socket client;
    private final OutputStream out;
    private final InputStream in;

    private String playerName;

    /**
     * Instantiates a new Client.
     *
     * @param host the host
     * @param port the port
     * @throws IOException if an in-/output error occurs
     */
    public Client(final String host, final int port) throws IOException{

        this.client = new Socket(host, port);

        this.out = client.getOutputStream();
        this.in = client.getInputStream();
    }

    /**
     * Close the connection to the server
     *
     * @throws IOException if an in-/output error occurs
     */
    public void closeConnection() throws IOException{
        if(!client.isClosed()) client.close();
    }

    /**
     * Check whether or not the connection to the server is closed.
     *
     * @return true if the connection is closed, false else
     */
    public boolean isConnectionClosed(){
        return client.isClosed();
    }

    /**
     * Returns an InputStream object of the client
     *
     * @return the input stream
     */
    public InputStream getInputStream(){return in;}

    /**
     * Returns an OutputStream object of the client
     *
     * @return the output stream
     */
    public OutputStream getOutputStream(){return out;}

    /**
     * Set the players name
     *
     * @param name the name
     */
    public void setPlayerName(final String name){
        this.playerName = name;
    }

    /**
     * Get the players name
     *
     * @return the string
     */
    public String getPlayerName(){
        return playerName;
    }
}