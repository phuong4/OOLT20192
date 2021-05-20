package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class MenuViewController extends View {



    List<DotsButton> menuButtons;
    List<DotPicker> dotsList;
    private DOTIMAGE choosenDot;
    private Dot dot;
    private DotsSubScene optionsSubScene;



    public MenuViewController() {
        menuButtons = new ArrayList<>();
        parentPane = new AnchorPane();
        gameScene = new Scene(parentPane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setResizable(false)
        ;
        gameStage.setScene(gameScene);
        createButtons();
        createBackground();
        createLogo();
        createSubScenes();

    }

    private void createSubScenes(){
        createDotChooserSubScene();
    }

    private void createDotChooserSubScene() {
        optionsSubScene = new DotsSubScene();
        parentPane.getChildren().add(optionsSubScene);

        InfoLabel chooseDotLabel = new InfoLabel("CHOOSE COLOR!!!");
        chooseDotLabel.setLayoutX(110);
        chooseDotLabel.setLayoutY(25);
        optionsSubScene.getPane().getChildren().add(chooseDotLabel);
        optionsSubScene.getPane().getChildren().add(createDotsToChoose());
        optionsSubScene.getPane().getChildren().add(createButtonToStart());
    }

    private HBox createDotsToChoose(){
        HBox box = new HBox();
        box.setSpacing(120);
        dotsList = new ArrayList<>();
        for(DOTIMAGE dot : DOTIMAGE.values()){
            DotPicker dotToPick = new DotPicker(dot);
            dotsList.add(dotToPick);
            box.getChildren().add(dotToPick);
            dotToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (DotPicker dot : dotsList){
                        dot.setIsCheckMarkChoosen(false);
                    }
                    dotToPick.setIsCheckMarkChoosen(true);
                    choosenDot = dotToPick.getDot();
                }
            });
        }
        box.setLayoutX(300 - (118*2));
        box.setLayoutY(100);
        return box;
    }

    public Stage getMainStage() {
        return gameStage;
    }

    private void createButtons() {
        createExitButton();
//        createStartButton();
        createGuideButton();
        createPlayButton();
    }

    private void createBackground() {
        Image backgroundImage = new Image("view/resources/menuimage/grey_panel2.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        parentPane.setBackground(new Background(background));
    }

    private void addMenuButton(DotsButton button) {
        button.setLayoutX(MENU_BUTTON_START_X + menuButtons.size() * 350);
        button.setLayoutY(MENU_BUTTON_START_Y);
        menuButtons.add(button);
        parentPane.getChildren().add(button);
    }

    private DotsButton createButtonToStart(){
        DotsButton startButton = new DotsButton("GO!");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if( choosenDot != null) {
                    GameViewController gameController = new GameViewController();
                    gameController.createNewGame(gameStage, choosenDot, dot);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning!!!");
                    alert.setContentText("You have to choose the color of dot first!");
                    alert.showAndWait();
                }
            }
        });
        return startButton;
    }

    private void createExitButton() {
        DotsButton exitButton = new DotsButton("Quit");
        addMenuButton(exitButton);
        exitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitButton.setEffect(new DropShadow());
            }
        });
        exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitButton.setEffect(null);
            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStage.close();
            }
        });
    }


    private void createGuideButton() {
        DotsButton guideButton = new DotsButton("Guide");
        addMenuButton(guideButton);
        guideButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                guideButton.setEffect(new DropShadow());
            }
        });
        guideButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                guideButton.setEffect(null);
            }
        });
        guideButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GuideViewController guide = new GuideViewController();
                guide.showGuide(gameStage);
            }
        });
    }

    private void createPlayButton() {
        DotsButton optionButton = new DotsButton("Play");
        addMenuButton(optionButton);
        optionButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                optionButton.setEffect(new DropShadow());
            }
        });
        optionButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                optionButton.setEffect(null);
            }
        });

        optionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                optionsSubScene.moveSubScene();
            }
        });
    }

    private void createLogo() {
        ImageView logo = new ImageView("view/resources/menuimage/dots_big_logo.png");
        logo.setLayoutX(100);
        logo.setLayoutY(50);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(new DropShadow());
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(null);
            }
        });
        parentPane.getChildren().add(logo);
    }


}
