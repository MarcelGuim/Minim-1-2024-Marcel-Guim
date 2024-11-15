package edu.upc.dsa.models;

import edu.upc.dsa.exceptions.WrongTypeException;
import edu.upc.dsa.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class Points {
    String id;
    double horizontal;
    double vertical;
    String type;
    List<User> userList;


    public Points() {
        this.setId(RandomUtils.getId());
    }
    public Points(double horizontal, double vertical, String type) throws WrongTypeException {this(null, horizontal,vertical,type);
    }

    public Points(String id,double horizontal, double vertical, String type) throws WrongTypeException {
        this();
        if(type.equals("DOOR") ||type.equals("WALL") ||type.equals("BRIDGE") ||type.equals("POTION") ||type.equals("SWORD") ||type.equals("COIN") ||type.equals("GRASS") ||type.equals("TREE")){
            this.setType(type);
            if (id != null) this.setId(id);
            this.setHorizontal(horizontal);
            this.setVertical(vertical);
            userList = new ArrayList<>();
        }
        else {
            throw new WrongTypeException();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(double horizontal) {
        this.horizontal = horizontal;
    }

    public double getVertical() {
        return vertical;
    }

    public void setVertical(double vertical) {
        this.vertical = vertical;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public void addUser(User u){
        this.userList.add(u);
    }

    @Override
    public String toString() {
        return "Point [id="+id+", horizontal=" + horizontal + ", vertical=" + vertical + "type= "+type+"]";
    }
}


