package model;


public enum DOTIMAGE {

    BLUE("view/resources/menuimage/dotchooser/new_blue_tick.png"),
    GREEN("view/resources/menuimage/dotchooser/new_green_tick.png");
//    RED("view/resources/dotschooser/red_dot.png"),
//    YELLOW("view/resources/dotschooser/1yellow_dot.png");


    private String urlDot;

    DOTIMAGE(String urlDot) {
        this.urlDot = urlDot;
    }

    public String getUrl() {
        return this.urlDot;
    }

}
