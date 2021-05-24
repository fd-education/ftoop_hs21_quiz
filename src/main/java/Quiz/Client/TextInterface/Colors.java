package Quiz.Client.TextInterface;

public enum Colors {
    RESET("\033[0m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),

    WHITE_BOLD("\033[1;37m"),
    RED_BOLD("\033[1;31m"),
    GREEN_BOLD("\033[1;32m"),
    YELLOW_BOLD("\033[1;33m"),
    BLUE_BOLD("\033[1;34m");

    private final String color;

    Colors(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }

    public String colorText(String text){
        return color + text + Colors.RESET.getColor();
    }
}

