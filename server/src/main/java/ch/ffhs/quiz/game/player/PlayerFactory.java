package ch.ffhs.quiz.game.player;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.logger.LoggerUtils;
import ch.ffhs.quiz.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Helper class that generates players.
 */
public class PlayerFactory {
    private static final Logger logger = LoggerUtils.getLogger();

    // To prevent an instantiation of this helper class
    private PlayerFactory() {}

    /**
     * Connects a given amount of players by accepting connections on the given server.
     * Incoming connections that give failures will be ignored.
     *
     * @param server      a running server
     * @param playerCount the amount of players that should be connected
     * @return a list of connected players the size of playerCount.
     */
    public static List<Player> connectPlayers(Server server, int playerCount) {
        Objects.requireNonNull(server);

        logger.info("Starting to connect %d players...".formatted(playerCount));
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerCount;) {
            try {
                logger.info("Connecting player %d".formatted(i));
                Socket clientSocket = server.acceptConnection();
                Connection connection = new ConnectionImpl(clientSocket.getOutputStream(), clientSocket.getInputStream());
                PlayerData playerData = new PlayerData(i);
                players.add(new Player(playerData, connection));
                logger.info("Player %d connected!".formatted(i));
                i++;
            } catch (IOException e) {
                logger.warning("Player could not connect with error %s. Trying again...%n".formatted(e.getCause()));
            }
        }
        logger.info("Successfully connected %d players!".formatted(playerCount));
        return players;
    }
}
