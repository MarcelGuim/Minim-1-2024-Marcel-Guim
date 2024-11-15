package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.Points;
import edu.upc.dsa.models.User;

import java.time.LocalDate;
import java.util.List;

public interface UserManager {

    public User addUser(String id, String name, String surname, String mail, LocalDate date);
    public User addUser(String name, String surname, String mail, LocalDate date);
    public User addUser(User user);
    public Points addPoint(String id, double horizontal, double vertical, ElementType type);
    public Points addPoint(double horizontal, double vertical, ElementType type);
    public Points addPoint(Points point);
    public User getUser(String id);
    public Points getPoint(String id);

    public List<User> GetOrderedUsers();
    public boolean RegisterInterestPoint(String id, double horizontal, double vertical);
    public List<Points> GetListOfPointsUser(String id);
    public List<User> GetListUsersOfPoint(double horizontal, double vertical);
    public List<Points> GetPointsOfType(ElementType type);
    public Points getPointByCoords(double horizontal, double vertical);
    public void clear();
    public int sizeUsers();
    public int sizePoints();
    public List<Points> GetAllPoints();
}
