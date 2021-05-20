package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DotsButton;
import model.View;


public class GuideViewController extends View {

    private Stage primaryStage;
    private static final ImageView exampleImage = new ImageView("view/resources/menuimage/guide_example.png");
    public GuideViewController() {
        initializeStage();
    }


    private void initializeStage() {
        parentPane = new AnchorPane();
        gameScene = new Scene(parentPane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        createBackButton();
    }

    public void showGuide(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.hide();
        guideIndex();
        createExampleImage(exampleImage);
        gameStage.show();
    }

    private void createExampleImage(ImageView exampleImage){
        exampleImage.setLayoutX(50);
        exampleImage.setLayoutY(400);
        parentPane.getChildren().add(exampleImage);
    }

    private void guideIndex() {
        Text text = new Text();
        Text source = new Text();
        text.setFont(new Font("Arial", 15));
        source.setFont(new Font("Arial", 25));
        text.setText(
                "Take more boxes than your opponent. You move by connecting two dots with a line. When you place the last \n" +
                        "wall’ of a single square (box), the box is yours. The players move in turn, but whenever a player takes \n" +
                        "a box (s)he must move again. The board game ends when all 25 boxes have been taken. The player with the \n" +
                        "most boxes wins. \n \n " +
                        "When all four of the lines around a single box are in place, the player who made the last move owns the box.\n" +
                        "The box is marked in that player’s color and (s)he must move again. \n \n" +
                        "The game is finished when all connections have been made and all boxes have been taken. The player who owns \n" +
                        "most boxes wins. \n \n" +
                        "On the surface this doesn’t look like a very strategic board game. A typical beginner’s game looks like this: \n" +
                        "in the beginning connections are made in more or less random fashion, and no boxes are taken. Only in the endgame \n" +
                        "one of the players is usually forced to give away boxes, and after that the players take turns in giving away ever-larger \n" +
                        "‘chains’ of boxes; the one who gets the last and largest chain usually wins. However, there are ways to force your  \n" +
                        "opponent to give away the long chains at the end."


        );

        source.setText("Source: youturnmyturn.com");

        text.setX(100);
        text.setY(50);
        source.setX(750);
        source.setY(450);
        parentPane.getChildren().addAll(text,source);
    }

    private void back() {
        this.gameStage.hide();
        this.primaryStage.show();
    }

    private void createBackButton() {
        DotsButton backButton = new DotsButton("Back");
        backButton.setLayoutX(90);
        backButton.setLayoutY(700);

        parentPane.getChildren().add(backButton);
        backButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                backButton.setEffect(new DropShadow());
            }
        });
        backButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                backButton.setEffect(null);
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                back();
            }
        });
    }
}
