package edu.upc.dsa.models;

import edu.upc.dsa.exceptions.WrongTypeException;
import edu.upc.dsa.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class Points {
    String id;
    double horizontal;
    double vertical;
    ElementType type;


    public Points() {
        this.setId(RandomUtils.getId());
    }
    public Points(double horizontal, double vertical, ElementType type) throws WrongTypeException {this(null, horizontal,vertical,type);
    }

    public Points(String id,double horizontal, double vertical, ElementType type) throws WrongTypeException {
        this();
        if (id != null) this.setId(id);
        this.setHorizontal(horizontal);
        this.setVertical(vertical);
        this.setType(type);
    }
    public void setRandomId(){
        this.setId(RandomUtils.getId());
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

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Point [id="+id+", horizontal=" + horizontal + ", vertical=" + vertical + "type= "+type+"]";
    }
}


