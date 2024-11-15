package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User {
    String id;
    String name;
    String surname;
    String mail;
    LocalDate date;
    List<Points> points;
    static int lastId;

    public User() {
        this.setId(RandomUtils.getId());
    }
    public User(String name, String surname, String mail, LocalDate date) {this(null, name, surname, mail,date);
    }

    public User(String id, String name, String surname, String mail, LocalDate date) {
        this();
        if (id != null) this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setMail(mail);
        this.setDate(date);
        points = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Points> getPoints() {
        return points;
    }

    public void setPoints(List<Points> points) {
        this.points = points;
    }
    public void addPoint(Points p){
        this.points.add(p);
    }

    @Override
    public String toString() {
        return "User [id="+id+", name=" + name + ", surname=" + surname + "mail= "+mail+ "date="+date+"]";
    }

}
