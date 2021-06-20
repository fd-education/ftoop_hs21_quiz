package ch.ffhs.quiz.game.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerFactoryTest {
    @Mock
    Socket socket;
    @Mock
    InputStream in;
    @Mock
    ServerSocket serverSocket;
    @Mock
    OutputStream out;

    @Test
    void connectPlayers_positive_simple() throws IOException {
        when(serverSocket.accept()).thenReturn(socket);
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);


        List<Player> players = PlayerFactory.connectPlayers(serverSocket, 2);

        assertEquals(0, players.get(0).getId());
        assertEquals(1, players.get(1).getId());
    }

    @Test
    void connectPlayers_positive_throwingClientIsIgnored() throws Exception {
        when(serverSocket.accept()).thenThrow(IOException.class).thenReturn(socket);
        when(socket.getInputStream()).thenThrow(IOException.class).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);


        String output = tapSystemErr(() -> {
            List<Player> players = PlayerFactory.connectPlayers(serverSocket, 2);

            assertEquals(0, players.get(0).getId());
            assertEquals(1, players.get(1).getId());
        });

        assertNotEquals(output.indexOf("Player could not connect with error null. Trying again..."),
                output.lastIndexOf("Player could not connect with error null. Trying again..."));
    }

    @Test
    void connectPlayers_negative_invalidArgsThrow() {
        assertThrows(NullPointerException.class, () -> PlayerFactory.connectPlayers(null, 2));
        assertThrows(IllegalArgumentException.class, () -> PlayerFactory.connectPlayers(serverSocket, -1));
    }
}