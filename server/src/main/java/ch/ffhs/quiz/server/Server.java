package ch.ffhs.quiz.server;

import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * A server where sockets can connect to.
 */
public class Server {
    private final ServerSocket serverSocket;

    /**
     * Instantiates a new server with the given port.
     *
     * @param port The server will run on this port. Must not be in use.
     * @throws IOException if an I/O Error has occurred during the creation. See {@link ServerSocket} for more details.
     */
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    /**
     * Accepts an incoming connection without a timeout.
     *
     * @return the connected socket
     * @throws IOException if an I/O Error occurs
     */
    public Socket acceptConnection() throws IOException {
        return acceptConnection(0, TimeUnit.MILLISECONDS);
    }

    /**
     * Accepts an incoming connection with a timeout in milliseconds.
     *
     * @param timeout the timeout duration in milliseconds
     * @return the connected socket
     * @throws IOException if an I/O Error occurs
     * @throws SocketTimeoutException if the given timeout has been consumed
     */
    public Socket acceptConnection(int timeout) throws IOException {
        return acceptConnection(timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Accepts an incoming connection with a timeout of a given amount of given timeunits.
     *
     * @param timeout  the amount of timeunits
     * @param timeUnit the time unit
     * @return the connected socket
     * @throws IOException if an I/O Error occurs
     * @throws SocketTimeoutException if the given timeout has been consumed
     */
    public Socket acceptConnection(int timeout, TimeUnit timeUnit) throws IOException {
        if (isStopped())
            throw new IllegalStateException("Could not accept Connection: Server was stopped.");
        try {
            serverSocket.setSoTimeout((int) timeUnit.toMillis(timeout));
            return serverSocket.accept();
        } finally {
            serverSocket.setSoTimeout(0);
        }
    }

    /**
     * Stops the server.
     *
     * @throws IOException if an I/O Error occurs
     */
    public void stop() throws IOException {
        serverSocket.close();
    }

    /**
     * Return whether the server has been stopped or not.
     *
     * @return true if the server has been stopped, false otherwise
     */
    public boolean isStopped() {
        return serverSocket.isClosed();
    }
}
