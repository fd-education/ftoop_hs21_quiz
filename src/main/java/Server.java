import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Server {
    private ServerSocket serverSocket;
    private long timeout = 10 * 1000;
    private long maxConnections = 5;
    private List<Socket> connectedSockets = new ArrayList<>();

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Server server = new Server();
        server.start(6666);
    }

    public void start(int port) throws IOException, ExecutionException, InterruptedException {
        final long startTime = System.currentTimeMillis();
        serverSocket = new ServerSocket(port);
        // Accepts new connections until time is up or max count of connections is reached
        try {
            while (connectedSockets.size() < maxConnections) {
                final long passedTime = System.currentTimeMillis() - startTime;
                serverSocket.setSoTimeout((int) (timeout - passedTime));
                Socket clientSocket = serverSocket.accept();
                connectedSockets.add(clientSocket);
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Not enough users are connected. Starting anyway");
        }
        ExecutorService executorService = Executors.newFixedThreadPool(connectedSockets.size());
        List<Callable<String>> callables = new ArrayList<>();
        for (Socket clientSocket : connectedSockets) {
            callables.add(new ClientHandler(clientSocket));
        }
        // Gets the string of the Clienthandler that first finished
        String result = executorService.invokeAny(callables);
        System.out.println(result);
        stop();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("Timeout cannot be smaller than zero");
        }
        this.timeout = timeout;
    }
}
