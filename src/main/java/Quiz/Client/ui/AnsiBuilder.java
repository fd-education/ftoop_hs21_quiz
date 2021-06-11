package Quiz.Client.ui;

@SuppressWarnings("unused")
public class AnsiBuilder {
    private int font;
    private int background;
    private String decoration = "";
    private final String text;

    public AnsiBuilder(String text) {
        this.text = text;
    }

    public AnsiBuilder font(Color color, Decoration decoration, boolean bright) {
        font(color, bright);

        this.decoration = decoration.getDecoration();

        return this;
    }

    public AnsiBuilder font(Color color, boolean bright) {
        if (bright) this.font = Property.FONT_BRIGHT.getProperty() + color.getColor();
        if (!bright) this.font = Property.FONT.getProperty() + color.getColor();

        return this;
    }

    public AnsiBuilder background(Color color, boolean bright) {
        if (bright) this.background = Property.BACKGROUND_BRIGHT.getProperty() + color.getColor();
        if (!bright) this.background = Property.BACKGROUND.getProperty() + color.getColor();

        return this;
    }

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

    public void print(){
        String text = create();
        System.out.println(text);
    }

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