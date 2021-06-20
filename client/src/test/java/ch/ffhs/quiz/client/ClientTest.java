package ch.ffhs.quiz.client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    static Client client;
    static ServerSocket server;

    @BeforeAll
    static void setup() throws IOException {
        server = new ServerSocket(3141);

        client = new Client("localhost", 3141);
    }

    @Test
    void ctorTest_Negative_HostNull() throws IOException{
        assertThrows(NullPointerException.class, () -> new Client(null, 1000));
        String EXPECTED = "host must not be null";

        try{
            new Client(null, 1000);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED, npe.getMessage());
        }
    }

    @Test
    void ctorTest_Negative_HostEmpty() throws IOException{
        assertThrows(IllegalArgumentException.class, () -> new Client(" ", 1000));
        String EXPECTED = "host must not be empty or consist of only whitespace";

        try{
            new Client(" ", 1000);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void ctorTest_Negative_PortNegative() throws IOException{
        assertThrows(IllegalArgumentException.class, () -> new Client("localhost", -1));
        String EXPECTED = "port must be greater than zero";

        try{
            new Client("localhost", -1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
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

    @Test
    void setPlayerNameTest_Negative_NameNull(){
        assertThrows(NullPointerException.class, () -> client.setPlayerName(null));
        String EXPECTED = "name must not be null";

        try{
            client.setPlayerName(null);
        } catch(NullPointerException nullPointerException){
            assertEquals(EXPECTED, nullPointerException.getMessage());
        }
    }

    @Test
    void setPlayerNameTest_Negative_NameEmpty(){
        assertThrows(IllegalArgumentException.class, () -> client.setPlayerName(" "));
        String EXPECTED = "name must not be empty or consist of only whitespace";

        try{
            client.setPlayerName(" ");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @AfterAll
    static void teardown() throws IOException{
        server.close();
    }
}