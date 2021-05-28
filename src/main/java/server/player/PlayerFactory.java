package server.player;

import server.Server;
import server.player.impl.PlayerConnectionImpl;
import server.player.impl.PlayerDataImpl;
import server.player.impl.PlayerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerFactory {
    public static List<Player> connectPlayers(Server server, int playerCount) {
        Objects.requireNonNull(server);

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            try {
                Socket clientSocket = server.acceptConnection();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PlayerConnection playerConnection = new PlayerConnectionImpl(writer, reader);
                PlayerData playerData = new PlayerDataImpl(i);
                players.add(new PlayerImpl(playerData, playerConnection));
            } catch (IOException e) {
                System.err.printf("Player could not connect with error %s. Trying again...", e.getCause());
                i--;
            }
        }
        return players;
    }
}
