package ch.ffhs.quiz.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.BindException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(10)
@ExtendWith(MockitoExtension.class)
class ServerTest {
    Server server;

    @BeforeEach
    void setUp() throws IOException {
        startServer();
    }

    private void startServer() throws IOException {
        server = new Server(6666);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (!server.isStopped())
            server.stop();
    }

    @Test
    void ctor_doublePortBindFails() {
        assertThrows(BindException.class, () -> new Server(6666));
    }

    @Test
    void ctor_invalidPortFails() {
        assertThrows(IllegalArgumentException.class, () -> new Server(-1));
    }

    @Test
    void getConnection_workingConnection() throws IOException {
        createConnectingSocket(200);

        Socket clientSocket = server.acceptConnection();

        assertTrue(clientSocket.isConnected(), "Connection could not be made.");
    }

    @Test
    void getConnection_stoppedServer() throws IOException {
        server.stop();

        assertThrows(IllegalStateException.class, server::acceptConnection, "Server was not properly stopped");
    }

    @Test
    void getConnection_positiveTimeout() throws IOException {
        createConnectingSocket(200);

        Socket clientSocket = server.acceptConnection(2, TimeUnit.SECONDS);

        assertTrue(clientSocket.isConnected(), "Connection could not be made.");
    }

    @Test
    void getConnection_negativeTimeout()  {
        createConnectingSocket(2000);

        assertThrows(SocketTimeoutException.class, () -> server.acceptConnection(10));
    }

    private void createConnectingSocket(int timeout) {
        new Thread(() -> {
            try {
                Thread.sleep(timeout);
                new Socket("localhost", 6666);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}