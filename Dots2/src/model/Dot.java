package model;


import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.GameViewController;
//import model.DotAttribute;

import java.util.ArrayList;

public class Dot extends DotsAction {

    public DotAttribute attribute;
    public ImageView dotImage = new ImageView();
    public static ArrayList<Dot> dots = new ArrayList<>();
    public boolean dotIsSet = new DotAttribute().status.isSet;


    public Dot(DOTIMAGE choosenDot){
        this.attribute = new DotAttribute();
        getDotImage(choosenDot);
        dots.add(this);
       chooseDot();
    }

    @Override
    public void chooseDot() {
        dotImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dotIsSet = !dotIsSet;
                GameViewController.drawFrame();
            }
        });
    }


    public void getDotImage(DOTIMAGE choosenDot){
        dotImage = new ImageView(choosenDot.getUrl());
    }


}
