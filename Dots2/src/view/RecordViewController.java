package view;

import com.sun.prism.impl.Disposer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.DotsButton;
import model.View;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class RecordViewController extends View {

    private Stage primaryStage;
    private static final ImageView exampleImage = new ImageView("view/resources/menuimage/guide_example.png");
    public RecordViewController() {
        initializeStage();
    }


    private void initializeStage() {
        parentPane = new AnchorPane();
        gameScene = new Scene(parentPane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        createBackButton();
    }

    public void showRecord(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.hide();
        recordIndex();
        gameStage.show();
    }

    public void recordIndex(){
        for (int i = 0; i < GameViewController.playerRecord.size(); i++) {
            Text x1 = new Text();
            Text x2 = new Text();
            Text turn = new Text();
            Text step = new Text();
            ArrayList<String> index = GameViewController.playerRecord.get(i);
            if (index.get(0) == "true"){
                turn.setText("Step " + (i+1) + ": Player 1");
                System.out.println("p1");
            }
            else{
                turn.setText("Step " + (i+1) + ": Player 2");
                System.out.println("p2");
            }
            x1.setText("[" + index.get(1) + "," + index.get(2) + "] - [" + index.get(3) + "," + index.get(4) + "]" );
            if (0 <= i && i <= 5) {
                turn.setX(200 * i + 60);
                turn.setY(30);
                x1.setX(200 * i + 50);
                x1.setY(50);
            }
            else if (6<=i && i<= 11){
                turn.setX(200 * (i - 6) + 60);
                turn.setY(80);
                x1.setX(200 * (i - 6) + 50);
                x1.setY(100);
            }
            else if (12 <= i && i<= 17){
                turn.setX(200 * (i - 12) + 60);
                turn.setY(130);
                x1.setX(200 * (i - 12) + 50);
                x1.setY(150);
            }
            else if (18<=i && i<= 23){
                turn.setX(200 * (i-18) + 60);
                turn.setY(180);
                x1.setX(200 * (i-18) + 50);
                x1.setY(200);
            }
            else if (24<=i && i<= 29){
                turn.setX(200 * (i-24) + 60);
                turn.setY(230);
                x1.setX(200 * (i-24) + 50);
                x1.setY(250);
            }
            else if (30<=i && i<= 35){
                turn.setX(200 * (i-30) + 60);
                turn.setY(280);
                x1.setX(200 * (i-30) + 50);
                x1.setY(300);
            }
            else if (36<=i && i<= 41){
                turn.setX(200 * (i-36) + 60);
                turn.setY(330);
                x1.setX(200 * (i-36) + 50);
                x1.setY(350);
            }
            else if (42<=i && i<= 47){
                turn.setX(200 * (i-42) + 60);
                turn.setY(380);
                x1.setX(200 * (i-42) + 50);
                x1.setY(400);
            }
            else if (48<=i && i<= 53){
                turn.setX(200 * (i-48) + 60);
                turn.setY(430);
                x1.setX(200 * (i-48) + 50);
                x1.setY(450);
            }
            else if (54<=i && i<= 56){
                turn.setX(200 * (i-54) + 60);
                turn.setY(480);
                x1.setX(200 * (i-54) + 50);
                x1.setY(500);
            }

            parentPane.getChildren().addAll(x1,turn);
        }
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
