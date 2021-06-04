package ch.ffhs.quiz.game.player;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.server.Server;
import ch.ffhs.quiz.game.player.impl.*;

import java.io.IOException;
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
                Connection connection = new ConnectionImpl(clientSocket.getOutputStream(), clientSocket.getInputStream());
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
