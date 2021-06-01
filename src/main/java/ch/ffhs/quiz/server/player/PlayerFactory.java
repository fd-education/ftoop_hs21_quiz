package ch.ffhs.quiz.server.player;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.server.Server;
import ch.ffhs.quiz.server.player.impl.*;

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
                Connection connection = new ConnectionImpl(writer, reader);
                PlayerData playerData = new PlayerDataImpl(i);
                players.add(new PlayerImpl(playerData, connection));
            } catch (IOException e) {
                System.err.printf("Player could not connect with error %s. Trying again...", e.getCause());
                i--;
            }
        }
        return players;
    }
}
