package server.player;

import org.junit.jupiter.api.Test;
import server.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerFactoryTest {

    @Test
    void connectPlayers_positive_simple() throws IOException {
        OutputStream out = mock(OutputStream.class);
        InputStream in = mock(InputStream.class);
        Server server = mock(Server.class);
        Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        when(server.acceptConnection()).thenReturn(socket);

        List<Player> players = PlayerFactory.connectPlayers(server, 2);

        assertEquals(0, players.get(0).getId());
        assertEquals(1, players.get(1).getId());
    }
}