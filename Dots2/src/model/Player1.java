package model;

public class Player1 extends Player {

    public boolean turn;
    public int point;

    public Player1(){
        this.turn = true;
        this.point = 0;
    }


    public void setTurn(boolean turn){
        this.turn = turn;
    }


    public void setPoint(int point){
        this.point = point;
    }
}
