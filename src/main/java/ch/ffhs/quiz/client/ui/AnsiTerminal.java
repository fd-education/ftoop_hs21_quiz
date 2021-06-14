package ch.ffhs.quiz.client.ui;

/**
 * Class contains logic for Terminal manipulation with ANSI
 */
public class AnsiTerminal {
    // ANSI Prefix String
    private static final String PREFIX = "\033[";

    /**
     * Moves the cursor up a certain amount of lines
     * @param lines number of lines to move the cursor
     */
    public static void moveCursorDown(final int lines){
        moveCursor(Direction.DOWN, lines);
    }

    /**
     * Moves the cursor to the left by a certain amount of chars
     * @param chars number of chars to move the cursor
     */
    public static void moveCursorLeft(final int chars){
        moveCursor(Direction.LEFT, chars);
    }

    /**
     * Moves the cursor to the right by a certain amount of chars
     * @param chars number of chars to move the cursor
     */
    public static void moveCursorRight(final int chars){
        moveCursor(Direction.RIGHT, chars);
    }

    // move the cursor on the terminal in a specified direction by a specified number of chars/ lines
    private static void moveCursor(final Direction direction, final int number){
        System.out.printf("%s%s%s", PREFIX, number, direction.getDirection());
    }

    /**
     * Sets the cursor to the specified position, starting at the top left at (0, 0)
     * @param line to place the cursor
     * @param column to place the cursor on the specified line
     */
    public static void positionCursor(final int line, final int column){
        String ansi = String.format("%s%s;%sH", PREFIX, line ,column);
        System.out.print(ansi);
    }

    // store the current cursor position
    public static void saveCursorPos(){
        String ansi = PREFIX + "s";
        System.out.print(ansi);
    }

    // go to the last saved cursor position
    public static void restoreCursorPos(){
        String ansi = PREFIX + "u";
        System.out.print(ansi);
    }

    /**
     * Clear the whole terminal
     */
    public static void clearTerminal(){
        String ansi = PREFIX + "H" + PREFIX + "2J";
        System.out.print(ansi);
    }

    /**
     * Clear the line where the cursor currently resides
     */
    public static void clearLine(){
        String ansi = PREFIX + "2K";
        System.out.print(ansi);
    }

    /**
     * Clear the line from cursor to the end of line
     */
    public static void clearRemainingOfLine(){
        String ansi = PREFIX + "K";
        System.out.print(ansi);
    }

    /**
     * Clears a specified number of lines,
     * starting with the current cursor position.
     * Restores cursor to the current position.
     * @param number how many lines to clear
     */
    public static void clearNumberOfLines(final int number){
        saveCursorPos();
        for(int i = 0; i < number; i++){
            clearLine();
            moveCursorDown(1);
        }
        restoreCursorPos();
    }

    // Available directions for cursor movements
    enum Direction{
        DOWN("E"),
        RIGHT("C"),
        LEFT("D");

        public final String direction;

        Direction(final String direction){this.direction = direction;}

        public String getDirection(){return direction;}
    }
}
