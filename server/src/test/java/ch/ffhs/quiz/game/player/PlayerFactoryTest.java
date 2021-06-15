package ch.ffhs.quiz.game.player;

import ch.ffhs.quiz.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerFactoryTest {
    Server server;
    Socket socket;
    InputStream in;

    @BeforeEach
    void setup() throws IOException {
        OutputStream out = mock(OutputStream.class);
        in = mock(InputStream.class);
        server = mock(Server.class);
        socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
    }

    @Test
    void connectPlayers_positive_simple() throws IOException {
        when(server.acceptConnection()).thenReturn(socket);

        List<Player> players = PlayerFactory.connectPlayers(server, 2);

        assertEquals(0, players.get(0).getId());
        assertEquals(1, players.get(1).getId());
    }

    @Test
    void connectPlayers_positive_throwingClientIsIgnored() throws Exception {
        when(server.acceptConnection()).thenThrow(IOException.class).thenReturn(socket);
        when(socket.getInputStream()).thenThrow(IOException.class).thenReturn(in);

        String output = tapSystemErr(() -> {
            List<Player> players = PlayerFactory.connectPlayers(server, 2);

            assertEquals(0, players.get(0).getId());
            assertEquals(1, players.get(1).getId());
        });

        assertNotEquals(output.indexOf("Player could not connect with error null. Trying again..."),
                output.lastIndexOf("Player could not connect with error null. Trying again..."));
    }
}