package Quiz.Client.ui;

public enum TextInterface {
    WELCOME("""
            ███████╗ █████╗  ██████╗ █████╗ ██████╗ ███████╗     ██████╗ ██╗   ██╗██╗███████╗
            ██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝    ██╔═══██╗██║   ██║██║╚══███╔╝
            █████╗  ███████║██║     ███████║██║  ██║█████╗      ██║   ██║██║   ██║██║  ███╔╝
            ██╔══╝  ██╔══██║██║     ██╔══██║██║  ██║██╔══╝      ██║▄▄ ██║██║   ██║██║ ███╔╝
            ██║     ██║  ██║╚██████╗██║  ██║██████╔╝███████╗    ╚██████╔╝╚██████╔╝██║███████╗
            ╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═════╝ ╚══════╝     ╚══▀▀═╝  ╚═════╝ ╚═╝╚══════╝
            """);

    private final String component;

    TextInterface(String component){
        this.component = component;
    }

    public String getComponent(){
        return component;
    }
}
