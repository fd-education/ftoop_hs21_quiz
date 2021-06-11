package ch.ffhs.quiz.client.ui;

/**
 * Class to work with text decorations and styles using ANSI
 */
@SuppressWarnings("unused")
public class AnsiBuilder {
    private int font;
    private int background;
    private String decoration = "";
    private final String text;

    public AnsiBuilder(String text) {
        this.text = text;
    }

    /**
     * Set the font color according to the passed properties
     * @param color for the font
     * @param decoration which style the text must have
     * @return AnsiBuilder instance
     */
    public AnsiBuilder setFont(Color color, Decoration decoration){
        setFont(color, decoration, false);
        return this;
    }

    /**
     * Set the font color according to the passed properties
     * @param color for the font
     * @param decoration which style the text must have
     * @param bright true if color must be bright, false otherwise
     * @return AnsiBuilder instance
     */
    public AnsiBuilder setFont(Color color, Decoration decoration, boolean bright) {
        setFont(color, bright);
        this.decoration = decoration.getDecoration();

        return this;
    }

    /**
     * Set the font color according to the passed properties
     * @param color for the font
     * @return AnsiBuilder instance
     */
    public AnsiBuilder setFont(Color color){
        return setFont(color, false);
    }

    /**
     * Set the font color according to the passed properties
     * @param color for the font
     * @param bright true if color must be bright, false otherwise
     * @return AnsiBuilder instance
     */
    public AnsiBuilder setFont(Color color, boolean bright) {
        if (bright) this.font = Property.FONT_BRIGHT.getProperty() + color.getColor();
        if (!bright) this.font = Property.FONT.getProperty() + color.getColor();

        return this;
    }

    /**
     * Set the background color according to the passed properties
     * @param color for the background
     * @return AnsiBuilder instance
     */
    public AnsiBuilder setBackground(Color color){
        return setBackground(color, false);
    }

    /**
     * Sets the Strings background according to the passed properties.
     * @param color for the background
     * @param bright true if color must be bright, false otherwise
     * @return AnsiBuilder instance
     */
    public AnsiBuilder setBackground(Color color, boolean bright) {
        if (bright) this.background = Property.BACKGROUND_BRIGHT.getProperty() + color.getColor();
        if (!bright) this.background = Property.BACKGROUND.getProperty() + color.getColor();

        return this;
    }

    /**
     * Creates the ANSI decorated text
     * @return String containing ANSI decorated text
     */
    public String create() {
        String ANSI_ESCAPE = "\033[";
        String ANSI_POSTFIX = "m";
        String ANSI_RESET = "\033[0m";

        StringBuilder sb = new StringBuilder();
        sb.append(ANSI_ESCAPE);

        if (!this.decoration.isBlank()) sb.append(this.decoration);

        if (this.font > 0) sb.append(";").append(this.font);

        if (this.background > 0) sb.append(";").append(this.background);

        sb.append(ANSI_POSTFIX).append(this.text).append(ANSI_RESET);
        return sb.toString();
    }

    /**
     * Prints the ANSI decorated text to the console
     */
    public void print(){
        String text = create();
        System.out.println(text);
    }

    /**
     * Enum contains decoration numbers used in an ANSI String
     */
    public enum Decoration {
        BOLD("1"),
        UNDERLINE("4");

        String decoration;

        Decoration(String decoration) {
            this.decoration = decoration;
        }

        public String getDecoration() {
            return this.decoration;
        }
    }

    /**
     * Enum contains property numbers used in an ANSI String
     */
    public enum Property {
        FONT(30),
        BACKGROUND(40),
        FONT_BRIGHT(90),
        BACKGROUND_BRIGHT(100);

        int property;

        Property(int property) {
            this.property = property;
        }

        public int getProperty() {
            return this.property;
        }
    }

    /**
     * Enum contains color numbers used in an ANSI String
     */
    public enum Color {
        BLACK(0),
        RED(1),
        GREEN(2),
        YELLOW(3),
        BLUE(4),
        MAGENTA(5),
        CYAN(6),
        WHITE(7);

        int color;

        Color(int color) {
            this.color = color;
        }

        public int getColor() {
            return this.color;
        }
    }
}