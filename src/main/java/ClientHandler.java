import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ClientHandler implements Callable<String> {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public String call() {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String greeting;
            while ((greeting = in.readLine()) != null) {
                System.out.println(greeting);
                if (greeting.startsWith("hello there")) {
                    out.println("general kenobi");
                    return "Nice " + greeting;
                } else {
                    out.println(greeting);
                }
            }
            System.out.println("Socket stopped sending");

        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Unexpected behavior occurred");
    }
}
