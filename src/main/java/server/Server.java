package server;
import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Server {
    private final ServerSocket serverSocket;

    /**
     * Instantiates a new server with the given port.
     *
     * @param port The server will run on this port. Must not be in use.
     *
     * @throws IOException if an I/O Error has occurred during the creation. See {@link ServerSocket} for more details.
     */
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public Socket acceptConnection() throws IOException {
        return acceptConnection(0, TimeUnit.MILLISECONDS);
    }

    public Socket acceptConnection(int timeout) throws IOException {
        return acceptConnection(timeout, TimeUnit.MILLISECONDS);
    }

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

    public void stop() throws IOException {
        serverSocket.close();
    }

    public boolean isStopped() {
        return serverSocket.isClosed();
    }
}
