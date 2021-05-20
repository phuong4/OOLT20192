package view;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

public class GameViewController extends View{

//    private static AnchorPane parentPane;
    private static Pane victoryPane, gamePane, buttonPane, backgroundPane, clock1Pane, clock2Pane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage primaryStage;
    private Dot dot;
    private DOTIMAGE choosenDot;
    List<DotsButton> gameButton;
    public ImageView dotImage = new ImageView();
    public static ArrayList<Dot> dotList = new ArrayList<>();
    public static ArrayList<ArrayList<String>> playerRecord = new ArrayList<ArrayList<String>>();
    public static ArrayList<String> currentRecord = new ArrayList<>();
    public static Dot dot1, dot2;
    private static ImageView horizontal;
    private static ImageView vertical;
    private static Player1 player1 = new Player1();
    private static Player2 player2 = new Player2();
    private static int count1, count2;
    private static ImageView xImage;
    private static ImageView oImage;
    private static ImageView player1Image, player1Image2;
    private static ImageView player2Image, player2Image2;



    public GameViewController() {
        initializeStage();
    }

    private void initializeStage() {
        parentPane = new AnchorPane();
        gamePane = new Pane();
        victoryPane = new Pane();
        buttonPane = new Pane();
        backgroundPane = new Pane();
        clock1Pane = new Pane();
        clock2Pane = new Pane();
        backgroundPane.getChildren().addAll(clock1Pane, clock2Pane);
        parentPane.getChildren().addAll(backgroundPane, gamePane, buttonPane);
        gameScene = new Scene(parentPane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }


    public void createNewGame(Stage primaryStage, DOTIMAGE choosenDot, Dot dot) {
        this.primaryStage = primaryStage;
        this.primaryStage.hide();
        gameStage.show();
        createGameDashboard(choosenDot, dot);
    }

    public void restart(){
        player1.setTurn(true);
        player2.setTurn(false);
        player1.setPoint(0);
        player2.setPoint(0);
        this.primaryStage.show();
        gameStage.hide();
    }


    private void createGameDashboard(DOTIMAGE choosenDot, Dot dot) {
        dotList.clear();
        playerRecord.clear();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                var rs = drawDot(choosenDot, i, j);
                dotList.add(rs);
            }
        }
        createRestartButton();
        createExitButton();
        createRecordButton();
        drawFrame();

    }

    public Dot drawDot(DOTIMAGE choosenDot, int positionDotX, int positionDotY) {
        dot = new Dot(choosenDot);
        dot.attribute.position.set(positionDotX + 1, positionDotY + 1);
        dot.attribute.status.set(false, false, false, false, false, 0);
        dot.dotImage.setLayoutX(DASHBOARD_START_X + 100 * positionDotX);
        dot.dotImage.setLayoutY(DASHBOARD_START_Y + 100 * positionDotY);
        return dot;
    }

    public static void matchTwoVerticalPoints(double x, double y) {
        vertical = new ImageView("view/resources/dotimage/vertical2.png");
        vertical.setLayoutX(DASHBOARD_START_X - 37 + 100 * (x - 1));
        vertical.setLayoutY(DASHBOARD_START_Y + 14 + 100 * (y - 1));
        gamePane.getChildren().add(vertical);
    }


    public static void matchTwoHorizontalPoint(double x, double y) {
        horizontal = new ImageView("view/resources/dotimage/horizontal2.png");
        horizontal.setLayoutX(DASHBOARD_START_X + 14 + 100 * (x - 1));
        horizontal.setLayoutY(DASHBOARD_START_Y - 35 + 100 * (y - 1));
        gamePane.getChildren().add(horizontal);
    }

    public static void addXImage(double x, double y) {
        xImage = new ImageView("view/resources/dotimage/x2.png");
        xImage.setLayoutX(DASHBOARD_START_X + 20 + 100 * (x - 1));
        xImage.setLayoutY(DASHBOARD_START_Y + 23 + 100 * (y - 1));
        gamePane.getChildren().add(xImage);
    }

    public static void addYImage(double x, double y) {
        oImage = new ImageView("view/resources/dotimage/oImage2.png");
        oImage.setLayoutX(DASHBOARD_START_X + 18 + 100 * (x - 1));
        oImage.setLayoutY(DASHBOARD_START_Y + 20 + 100 * (y - 1));
        gamePane.getChildren().add(oImage);
    }


    public static void drawFrame() {
        gamePane.getChildren().clear();
        gameBackground1Image();
        gameBackground2Image();
        createPlayer1Image();
        createPlayer2Image();
        setTurnImage();
        playerMove();
        matchTwoPoints();
        checkPoint();
        System.out.println("draw frame");
        System.out.println("__________________");

    }

    private static void matchTwoPoints() {
        for (var dot : dotList) {
            if (dotList.indexOf(dot) >= 32) break;
            dot1 = dot;
            dot2 = dotList.get(dotList.indexOf(dot) + 8);
            if (dot1.attribute.status.right && dot2.attribute.status.left) {
                matchTwoHorizontalPoint(dot1.attribute.position.x, dot1.attribute.position.y);
            }
        }
        for (var dot : dotList) {
            if (dotList.indexOf(dot) != 7
                    && dotList.indexOf(dot) != 15
                    && dotList.indexOf(dot) != 23
                    && dotList.indexOf(dot) != 31
                    && dotList.indexOf(dot) != 39) {
                dot1 = dot;
                dot2 = dotList.get(dotList.indexOf(dot) + 1);
                if (dot1.attribute.status.bottom & dot2.attribute.status.top) {
                    matchTwoVerticalPoints(dot1.attribute.position.x, dot1.attribute.position.y);
                }
            }
        }
    }

    private static void playerMove() {

        checkMatchablePoint();

        for (var dot : dotList) {
            if (dot.dotIsSet) {
                dot.dotImage.setEffect(new DropShadow());
            } else {
                dot.dotImage.setEffect(null);
            }
            //TODO: Noi 2 diem lai voi nhau. Fix loi draw horizontal. DOne
            gamePane.getChildren().add(dot.dotImage);
        }
    }

    private static void checkMatchablePoint() {
        setTurnImage();
        var count = 0;
        for (var dot :
                dotList) {
            if (dot.dotIsSet) {
                count++;
            }
        }
        if (count >= 2) {
            for (var dot : dotList) {
                if (dot.dotIsSet) {
                    dot1 = dot;
                    break;
                }
            }
            for (var dot : dotList) {
                if (dot.dotIsSet && dot != dot1) {
                    dot2 = dot;
                    break;
                }
            }

            //TODO: 1 nước ăn 2 ô chỉ tính 1 ô. Fix!!

            if (abs(dot1.attribute.position.x - dot2.attribute.position.x) == 1
                    && abs(dot1.attribute.position.y - dot2.attribute.position.y) == 0
                    && !dot1.attribute.status.right
                    && !dot2.attribute.status.left) {
                currentRecord = new ArrayList<>();
                currentRecord.add(String.valueOf(player1.turn));
                currentRecord.add(String.valueOf(dot1.attribute.position.x));
                currentRecord.add(String.valueOf(dot1.attribute.position.y));
                currentRecord.add(String.valueOf(dot2.attribute.position.x));
                currentRecord.add(String.valueOf(dot2.attribute.position.y));
                playerRecord.add(currentRecord);
                if (dot1.attribute.position.x < dot2.attribute.position.x) {
                    dot1.attribute.status.right = true;
                    dot2.attribute.status.left = true;
                    dotList.set(dotList.indexOf(dot1), dot1);
                    dotList.set(dotList.indexOf(dot2), dot2);
                    if (dotList.indexOf(dot1) != 0
                            && dotList.indexOf(dot1) != 8
                            && dotList.indexOf(dot1) != 16
                            && dotList.indexOf(dot1) != 24
                            && dotList.indexOf(dot1) != 32
                    ) {
                        if (!hitAPoint(dot1) && !hitAPoint(dotList.get(dotList.indexOf(dot1) - 1))) {
                            setTurnImage();
                            switchTurn();
                            setTurnImage();
                            System.out.println("Switch turn");
                            System.out.println("Player1 turn " + player1.turn);
                            System.out.println("Player2 turn " + player2.turn);
                        } else if (hitAPoint(dot1) || hitAPoint(dotList.get(dotList.indexOf(dot1) - 1))) {
                            if (hitAPoint(dot1)) {
                                if (player1.turn) {
                                    dot1.attribute.status.checkPoint = 1;
                                } else if (player2.turn) {
                                    dot1.attribute.status.checkPoint = 2;
                                }
                                setTurnImage();
                            }
                            if (hitAPoint(dotList.get(dotList.indexOf(dot1) - 1))){
                                if (player1.turn) {
                                    dotList.get(dotList.indexOf(dot1) - 1).attribute.status.checkPoint = 1;
                                } else if (player2.turn) {
                                    dotList.get(dotList.indexOf(dot1) - 1).attribute.status.checkPoint = 2;
                                }
                                setTurnImage();
                            }
                        }

                    } else if (dotList.indexOf(dot1) == 0
                            || dotList.indexOf(dot1) == 8
                            || dotList.indexOf(dot1) == 16
                            || dotList.indexOf(dot1) == 24
                            || dotList.indexOf(dot1) == 32) {
                        if (!hitAPoint(dot1)) {
                            setTurnImage();
                            switchTurn();
                            setTurnImage();
                            System.out.println("Switch turn");
                            System.out.println("Player1 turn " + player1.turn);
                            System.out.println("Player2 turn " + player2.turn);
                        } else if (hitAPoint(dot1)) {
                            System.out.println("Hit a point1!");
                            if (player1.turn) {
                                dot1.attribute.status.checkPoint = 1;
                            } else if (player2.turn) {
                                dot1.attribute.status.checkPoint = 2;
                            }
                            setTurnImage();
                        }

                    }
                }

            } else if (abs(dot1.attribute.position.x - dot2.attribute.position.x) == 0
                    && abs(dot1.attribute.position.y - dot2.attribute.position.y) == 1
                    && !dot1.attribute.status.bottom
                    && !dot2.attribute.status.top) {
                currentRecord = new ArrayList<>();
                currentRecord.add(String.valueOf(player1.turn));
                currentRecord.add(String.valueOf(dot1.attribute.position.x));
                currentRecord.add(String.valueOf(dot1.attribute.position.y));
                currentRecord.add(String.valueOf(dot2.attribute.position.x));
                currentRecord.add(String.valueOf(dot2.attribute.position.y));
                playerRecord.add(currentRecord);
                if (dot1.attribute.position.y < dot2.attribute.position.y) {
                    dot1.attribute.status.bottom = true;
                    dot2.attribute.status.top = true;
                    dotList.set(dotList.indexOf(dot1), dot1);
                    dotList.set(dotList.indexOf(dot2), dot2);
                    if (dotList.indexOf(dot1) != 0
                            && dotList.indexOf(dot1) != 1
                            && dotList.indexOf(dot1) != 2
                            && dotList.indexOf(dot1) != 3
                            && dotList.indexOf(dot1) != 4
                            && dotList.indexOf(dot1) != 5
                            && dotList.indexOf(dot1) != 6
                            && dotList.indexOf(dot1) != 7
                    ) {
                        if (!hitAPoint(dot1) && !hitAPoint(dotList.get(dotList.indexOf(dot1) - 8))) {
                            switchTurn();
                            setTurnImage();
                            System.out.println("Switch turn");
                            System.out.println("Player1 turn " + player1.turn);
                            System.out.println("Player2 turn " + player2.turn);
                        } else if (hitAPoint(dot1) || hitAPoint(dotList.get(dotList.indexOf(dot1) - 8))) {
                            if (hitAPoint(dot1)) {
                                if (player1.turn) {
                                    dot1.attribute.status.checkPoint = 1;
                                } else if (player2.turn) {
                                    dot1.attribute.status.checkPoint = 2;
                                }
                                setTurnImage();
                            }
                            if (hitAPoint(dotList.get(dotList.indexOf(dot1) - 8))){
                                if (player1.turn) {
                                    dotList.get(dotList.indexOf(dot1) - 8).attribute.status.checkPoint = 1;
                                } else if (player2.turn) {
                                    dotList.get(dotList.indexOf(dot1) - 8).attribute.status.checkPoint = 2;
                                }
                            }
                            setTurnImage();
                        }

                    } else if (dotList.indexOf(dot1) == 0
                            || dotList.indexOf(dot1) == 1
                            || dotList.indexOf(dot1) == 2
                            || dotList.indexOf(dot1) == 3
                            || dotList.indexOf(dot1) == 4
                            || dotList.indexOf(dot1) == 5
                            || dotList.indexOf(dot1) == 6
                            || dotList.indexOf(dot1) == 7) {
                        if (!hitAPoint(dot1)) {
                            switchTurn();
                            setTurnImage();
                            System.out.println("Switch turn");
                            System.out.println("Player1 turn " + player1.turn);
                            System.out.println("Player2 turn " + player2.turn);
                        } else if (hitAPoint(dot1)) {
                            if (player1.turn) {
                                dot1.attribute.status.checkPoint = 1;
                            } else if (player2.turn) {
                                dot1.attribute.status.checkPoint = 2;
                            }
                            setTurnImage();
                        }
                    }
                }
            }
            for (var dot : dotList) {
                dot.dotIsSet = false;
            }
        }
        setTurnImage();
    }

    //TODO: Create Player. Done

    private static void createPlayer1Image() {
        player1Image = new ImageView("view/resources/dotimage/player1.png");
        player1Image2 = new ImageView("view/resources/dotimage/x2.png");
        player1Image.setLayoutX(10);
        player1Image.setLayoutY(30);
        player1Image2.setLayoutX(168);
        player1Image2.setLayoutY(150);
        gamePane.getChildren().add(player1Image);
        gamePane.getChildren().add(player1Image2);

    }



    private static void createPlayer2Image() {
        player2Image = new ImageView("view/resources/dotimage/player2.png");
        player2Image2 = new ImageView("view/resources/dotimage/oImage2.png");
        player2Image.setLayoutX(830);
        player2Image.setLayoutY(30);
        player2Image2.setLayoutX(960);
        player2Image2.setLayoutY(150);
        gamePane.getChildren().add(player2Image);
        gamePane.getChildren().add(player2Image2);
    }

    private static void setTurnImage(){
        ImageView p1SandClock = new ImageView("view/resources/dotimage/sandclock.gif");
        ImageView p2SandClock = new ImageView("view/resources/dotimage/sandclock.gif");
        p1SandClock.setLayoutX(127);
        p1SandClock.setLayoutY(260);
        p2SandClock.setLayoutX(927);
        p2SandClock.setLayoutY(260);
        clock1Pane.getChildren().clear();
        clock2Pane.getChildren().clear();
        clock1Pane.getChildren().add(p1SandClock);
        clock2Pane.getChildren().add(p2SandClock);
        if (player1.turn){
            clock1Pane.toFront();
            clock2Pane.toBack();
            player1Image.setEffect(new DropShadow());
            player2Image.setEffect(new Lighting());
        }
        else if (player2.turn){
            clock1Pane.toBack();
            clock2Pane.toFront();
            player2Image.setEffect(new DropShadow());
            player1Image.setEffect(new Lighting());
        }
    }


    //TODO: Make player's turn

    public static void switchTurn() {
        player1.setTurn(!player1.turn);
        player2.setTurn(!player2.turn);
    }


    //TODO: Checklogic if player hit a point
    public static void checkPoint() {
        count1 = 0;
        count2 = 0;
        for (var dot : dotList) {
            if (dot.attribute.status.checkPoint == 1) {
                addXImage(dot.attribute.position.x, dot.attribute.position.y);
                count1++;
            } else if (dot.attribute.status.checkPoint == 2) {
                addYImage(dot.attribute.position.x, dot.attribute.position.y);
                count2++;
            }
        }
        player1.setPoint(count1);
        player2.setPoint(count2);
        Text text1 = new Text();
        Text text2 = new Text();
        System.out.println("Player1 point: " + player1.point + " count " + count1);
        System.out.println("Player2 point: " + player2.point + " count " + count2);
        text1.setText(Integer.toString(player1.point));
        text2.setText(Integer.toString(player2.point));
        text1.setFont(new Font("Arial", 60));
        text2.setFont(new Font("Arial", 60));
        text1.setX(180);
        text1.setY(450);
        text2.setX(980);
        text2.setY(450);
        gamePane.getChildren().add(text1);
        gamePane.getChildren().add(text2);
        if (player1.point + player2.point >= 28){
            endGame();
        }
    }

    private static boolean hitAPoint(Dot dot) {
        if (
                dot.attribute.status.right
                        && dot.attribute.status.bottom
                        && dotList.get(dotList.indexOf(dot) + 1).attribute.status.top
                        && dotList.get(dotList.indexOf(dot) + 1).attribute.status.right
                        && dotList.get(dotList.indexOf(dot) + 8).attribute.status.left
                        && dotList.get(dotList.indexOf(dot) + 8).attribute.status.bottom
                        && dotList.get(dotList.indexOf(dot) + 9).attribute.status.top
                        && dotList.get(dotList.indexOf(dot) + 9).attribute.status.left) {
            System.out.println("true1");

            return true;
        } else return false;
    }

    private static void gameBackground1Image(){
        ImageView player1Background = new ImageView("view/resources/dotimage/background_grey2.png");
        player1Background.setLayoutX(0);
        player1Background.setLayoutY(0);
        backgroundPane.getChildren().add(player1Background);
    }

    private static void gameBackground2Image(){
        ImageView player2Background = new ImageView("view/resources/dotimage/background_grey.png");
        player2Background.setLayoutX(820);
        player2Background.setLayoutY(0);
        backgroundPane.getChildren().add(player2Background);
    }


    private void createRestartButton() {
        DotsButton restartButton = new DotsButton("Restart");
        System.out.println(player2.point);
            restartButton.setLayoutX(90);
            restartButton.setLayoutY(650);

//        gameButton.add(restartButton);
        buttonPane.getChildren().add(restartButton);
        drawFrame();
        restartButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                restartButton.setEffect(new DropShadow());
            }
        });
        restartButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                restartButton.setEffect(null);
            }
        });
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                restart();
            }
        });
    }

    private void createExitButton() {
        DotsButton restartButton = new DotsButton("Quit");
        System.out.println(player2.point);
        restartButton.setLayoutX(90);
        restartButton.setLayoutY(730);

        buttonPane.getChildren().add(restartButton);
        restartButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                restartButton.setEffect(new DropShadow());
            }
        });
        restartButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                restartButton.setEffect(null);
            }
        });
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStage.close();
            }
        });
    }

    private void createRecordButton() {
        DotsButton recordButton = new DotsButton("Record");
        System.out.println(player2.point);
        recordButton.setLayoutX(90);
        recordButton.setLayoutY(570);

        buttonPane.getChildren().add(recordButton);
        recordButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                recordButton.setEffect(new DropShadow());
            }
        });
        recordButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                recordButton.setEffect(null);
            }
        });
        recordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RecordViewController record = new RecordViewController();
                record.showRecord(gameStage);
            }
        });
    }



    private static void endGame() {
        System.out.println("end game");
        //todo: end game.
        gamePane.setOpacity(0.3);
//        victoryPane.toFront();
        parentPane.getChildren().add(victoryPane);

        ImageView victory = new ImageView("view/resources/dotimage/victory2.png");
        ImageView player1Victory = new ImageView("view/resources/dotimage/small_player1.png");
        ImageView player2Victory = new ImageView("view/resources/dotimage/small_player2.png");
        ImageView firework = new ImageView("view/resources/dotimage/firework.gif");
        victory.setLayoutX(250);
        victory.setLayoutY(200);
        firework.setLayoutX(350);
        firework.setLayoutY(25);
        player1Victory.setLayoutX(450);
        player1Victory.setLayoutY(450);
        player2Victory.setLayoutX(450);
        player2Victory.setLayoutY(450);
        victory.setEffect(new DropShadow());
        player1Victory.setEffect(new DropShadow());
        player2Victory.setEffect(new DropShadow());
        victoryPane.getChildren().addAll(victory, firework);

        if (player1.point > player2.point) {
            victoryPane.getChildren().add(player1Victory);
        } else if (player2.point > player1.point) {
            victoryPane.getChildren().add(player2Victory);
        }

    }

}
