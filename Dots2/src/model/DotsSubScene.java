package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class DotsSubScene extends SubScene {

    private final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private final static String BACKGROUND_IMAGE = "view/resources/menuimage/grey_panel.png";
    private boolean isHidden;

    public DotsSubScene() {
        super(new AnchorPane(), 600, 400);
        prefWidth(600);
        prefHeight(400);

        BackgroundImage image = new BackgroundImage(
                new Image(BACKGROUND_IMAGE, 600, 400, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));
        isHidden = true;

        setLayoutX(1200);
        setLayoutY(160);
    }

    public void moveSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if(isHidden) {
            transition.setToX(-700);
            isHidden = false;
        }
        else{
            transition.setToX(0);
            isHidden = true;
        }
        transition.play();
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
}
