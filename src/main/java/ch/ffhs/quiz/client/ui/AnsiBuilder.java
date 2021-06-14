package ch.ffhs.quiz.client.ui;

import java.util.Objects;

/**
 * Class to work with text decorations and styles using ANSI
 */
public class AnsiBuilder {
    private int font;
    private int background;
    private final String text;

    public AnsiBuilder(final String text) {
        if(text.isBlank()) throw new IllegalArgumentException("text must contain letters and not be only whitespace");
        this.text = text;
    }

    /**
     * Set the font color according to the passed properties
     * @param color for the font
     * @param bright true if color must be bright, false otherwise
     * @return AnsiBuilder instance
     */
    public AnsiBuilder setFont(final Color color, boolean bright) {
        Objects.requireNonNull(color, "color must not be null");

        if (bright) this.font = Property.FONT_BRIGHT.getProperty() + color.getColor();
        if (!bright) this.font = Property.FONT.getProperty() + color.getColor();

        return this;
    }

    /**
     * Sets the Strings background according to the passed properties.
     * @param color for the background
     * @param bright true if color must be bright, false otherwise
     * @return AnsiBuilder instance
     */
    public AnsiBuilder setBackground(final Color color, boolean bright) {
        Objects.requireNonNull(color, "color must not be null");

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
        String BOLD = "1;";

        StringBuilder sb = new StringBuilder();
        sb.append(ANSI_ESCAPE).append(BOLD);

        if (this.font > 0) sb.append(this.font);
        if (this.background > 0) sb.append(";").append(this.background);

        sb.append(ANSI_POSTFIX).append(this.text).append(ANSI_RESET);
        return sb.toString();
    }

    /**
     * Prints the ANSI decorated text to the console
     */
    public void print(){
        String text = create();
        System.out.print(text);
    }

    public void println(){
        String text = create();
        System.out.println(text);
    }

    /**
     * Enum contains property numbers used in an ANSI String
     */
    public enum Property {
        FONT(30),
        BACKGROUND(40),
        FONT_BRIGHT(90),
        BACKGROUND_BRIGHT(100);

        private final int property;

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
        RED(1),
        GREEN(2),
        YELLOW(3),
        BLUE(4);

        private final int color;

        Color(int color) {
            this.color = color;
        }

        public int getColor() {
            return this.color;
        }
    }
}