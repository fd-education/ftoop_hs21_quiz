package ch.ffhs.quiz.client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientTest {
    static Client client;
    static ServerSocket server;

    @BeforeAll
    static void setup() throws IOException {
        server = new ServerSocket(3141);

        client = new Client("localhost", 3141);
    }

    @Test
    void closeConnectionTest() throws IOException {
        client.closeConnection();

        assertTrue(client.isConnectionClosed());
    }

    @Test
    void setPlayerNameTest() {
        String NAME = "Player";
        client.setPlayerName(NAME);
        assertEquals(NAME, client.getPlayerName());
    }

    @AfterAll
    static void teardown() throws IOException{
        server.close();
    }
}