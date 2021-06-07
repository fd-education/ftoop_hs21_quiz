package ch.ffhs.quiz.game.player;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.logger.LoggerUtils;
import ch.ffhs.quiz.server.Server;
import ch.ffhs.quiz.game.player.impl.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class PlayerFactory {
    private static final Logger logger = LoggerUtils.getLogger();

    public static List<Player> connectPlayers(Server server, int playerCount) {
        Objects.requireNonNull(server);

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerCount;) {
            try {
                Socket clientSocket = server.acceptConnection();
                Connection connection = new ConnectionImpl(clientSocket.getOutputStream(), clientSocket.getInputStream());
                PlayerData playerData = new PlayerDataImpl(i);
                players.add(new PlayerImpl(playerData, connection));
                i++;
            } catch (IOException e) {
                logger.warning("Player could not connect with error %s. Trying again...%n".formatted(e.getCause()));
            }
        }
        return players;
    }
}
