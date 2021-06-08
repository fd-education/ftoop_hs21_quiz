package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;

public abstract class MainGameStep extends GameStep{
    protected final RoundContext roundContext;

    public MainGameStep(GameContext gameContext) {
        super(gameContext);
        this.roundContext = gameContext.getRoundContext();
    }
}
