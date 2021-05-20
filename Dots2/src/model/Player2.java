package model;

public class Player2 extends Player{

    public boolean turn;
    public int point;

    public Player2(){
        this.turn = false;
        this.point = 0;
    }

    public void setTurn(boolean turn){
        this.turn = turn;
    }

    public void setPoint(int point){
        this.point = point;
    }
}
