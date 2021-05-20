package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MenuViewController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            MenuViewController controller = new MenuViewController();
            primaryStage = controller.getMainStage();
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
