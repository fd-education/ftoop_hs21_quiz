package ch.ffhs.quiz.messages;

public class ScoreboardMessage extends Message{
    public ScoreboardMessage(String scoreboard) {
        this.text = scoreboard;
    }
}
