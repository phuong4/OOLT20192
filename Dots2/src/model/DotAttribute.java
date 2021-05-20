package model;

import java.util.ArrayList;

public class DotAttribute {

    public Vector position;
    public Status status;
    public static ArrayList<DotAttribute> attributes = new ArrayList<>();
//    public boolean isSet;

    public DotAttribute(){
        this.position = new Vector();
        this.status = new Status();
        attributes.add(this);
//        System.out.println("Position = " + this.position );
    }
    

}
