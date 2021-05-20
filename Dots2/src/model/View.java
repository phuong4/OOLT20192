package model;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class View {

    public static final int GAME_HEIGHT = 800;
    public static final int GAME_WIDTH = 1200;
    public final static int MENU_BUTTON_START_X = 150;
    public final static int MENU_BUTTON_START_Y = 600;
    public final static int DASHBOARD_START_X = 390;
    public final static int DASHBOARD_START_Y = 40;

    public static AnchorPane parentPane;
    public Scene gameScene;
    public Stage gameStage;

    public View(){
        parentPane = new AnchorPane();
        gameScene = new Scene(parentPane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
    }
}
