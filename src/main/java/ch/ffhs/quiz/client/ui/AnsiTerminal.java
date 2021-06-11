package ch.ffhs.quiz.client.ui;

/**
 * Class contains logic for Terminal manipulation with ANSI
 */
public class AnsiTerminal {
    // ANSI Prefix String
    private final String PREFIX = "\033[";

    public AnsiTerminal(){}

    /**
     * Moves the cursor down a certain amount of lines
     * @param lines number of lines to move the cursor
     */
    public void moveCursorUp(int lines){
        moveCursor(Direction.UP, lines);
    }

    /**
     * Moves the cursor up a certain amount of lines
     * @param lines number of lines to move the cursor
     */
    public void moveCursorDown(int lines){
        moveCursor(Direction.DOWN, lines);
    }

    /**
     * Moves the cursor to the left by a certain amount of chars
     * @param chars number of chars to move the cursor
     */
    public void moveCursorLeft(int chars){
        moveCursor(Direction.LEFT, chars);
    }

    /**
     * Moves the cursor to the right by a certain amount of chars
     * @param chars number of chars to move the cursor
     */
    public void moveCursorRight(int chars){
        moveCursor(Direction.RIGHT, chars);
    }

    // move the cursor on the terminal in a specified direction by a specified number of chars/ lines
    private void moveCursor(Direction direction, int number){
        System.out.printf("%s%s%s", PREFIX, number, direction.getDirection());
    }

    /**
     * Clear the whole terminal
     */
    public void clearTerminal(){
        String ansi = PREFIX + "H" + PREFIX + "2J";
        System.out.print(ansi);
    }

    /**
     * Clear the line where the cursor currently resides
     */
    public void clearLine(){
        String ansi = PREFIX + "{2}K";
        System.out.print(ansi);
    }

    // Available directions for cursor movements
    enum Direction{
        UP("F"),
        DOWN("E"),
        RIGHT("C"),
        LEFT("D");

        public final String direction;

        Direction(String direction){this.direction = direction;}

        public String getDirection(){return direction;}
    }
}
