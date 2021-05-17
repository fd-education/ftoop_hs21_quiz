import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {
    List<Client> clients = new ArrayList<>();
    Random random = new Random();
    Server server;

    @BeforeEach
    void setUp() throws IOException, ExecutionException, InterruptedException {
        new Thread(() -> {
            server = new Server();
            try {
                server.start(6666);
            } catch (IOException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    server.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        for (int i = 0; i < 4; i++) {
            Client client = new Client(i);
            client.startConnection("localhost", 6666);
            clients.add(client);
        }
    }

    @Test
    void check_connection() throws IOException {
        for (Client client : clients) {
            String resp1 = client.sendMessage("hello");
            String resp2 = client.sendMessage("world");
            String resp3 = client.sendMessage("!");
            if (random.nextBoolean()) {
                String resp4 = client.sendMessage("hello there "+client.getId());
            }

            assertEquals("hello", resp1);
            assertEquals("world", resp2);
            assertEquals("!", resp3);
        }
    }
}