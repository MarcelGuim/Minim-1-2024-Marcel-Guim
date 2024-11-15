package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.Points;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class UserManagerImpl implements UserManager{
    private static UserManager instance;
    protected HashMap<String,User> users;
    protected List<Points> points;
    protected HashMap<String,List<Points>> pointsOfUser;
    protected HashMap<String,List<User>> usersOfPoint;
    final static Logger logger = Logger.getLogger(UserManagerImpl.class);

    private UserManagerImpl() {
        this.points = new LinkedList<>();
        this.users = new HashMap<>();
        this.usersOfPoint = new HashMap<>();
        this.pointsOfUser = new HashMap<>();
    }

    public static UserManager getInstance() {
        if (instance==null) instance = new UserManagerImpl();
        return instance;
    }
    public User addUser(String id, String name, String surname, String mail, LocalDate date){
        return this.addUser(new User(id, name,surname,mail,date));
    };
    public User addUser(String name, String surname, String mail, LocalDate date){
        return this.addUser(null, name,surname,mail,date);
    };
    public User addUser(User user){
        //Les exceptions les tracto aqui, així ho tinc tot mes controlat.
        try{
            if(users.containsKey(user.getMail())){
                logger.warn("Attention, user= "+user+" already exists");
                throw new RepeatedUserException();
            }
            else{
                logger.info("new User " + user);
                this.users.put(user.getMail(),user);
                this.pointsOfUser.put(user.getMail(),new ArrayList<>());
                logger.info("new user added");
                return user;
            }
        }
        catch(RepeatedUserException ex){
            return null;
        }
    };

    public Points addPoint(String id, double horizontal, double vertical, ElementType type){
        try{
            return this.addPoint(new Points(id, horizontal,vertical,type));
        }
        catch (WrongTypeException ex){
            logger.warn("Attention, wrong Type");
            return null;
        }

    };
    public Points addPoint(double horizontal, double vertical, ElementType type){
        return this.addPoint(null, horizontal, vertical, type);
    };
    public Points addPoint(Points point){
        logger.info("new Point " + point);
        this.points.add(point);
        this.usersOfPoint.put(point.getId(),new ArrayList<>());
        logger.info("new Point added");
        return point;
    };

    public User getUser(String id){
        try{
            logger.info("getUser("+id+")");
            for(User u: users.values()){
                if(u.getId().equals(id))
                    return u;
            }
            logger.warn("Attention, user not foudn");
            throw new UserNotFoundException();
        }
        catch(UserNotFoundException ex){
            return null;
        }
    };
    public Points getPoint(String id){
        logger.info("Get point with Id = "+id);
        try{
            for(Points p: points){
                if(p.getId().equals(id)){
                    logger.info("Point found= "+p);
                    return p;
                }
            }
            logger.warn("Attention, point("+id+") not found");
            throw new PointNotFoundException();
        }
        catch (PointNotFoundException ex){
            return null;
        }
    };

    public List<User> GetOrderedUsers(){
        logger.info("Ordered Users List");
        List<User> listaRespuesta = new ArrayList<>(users.values());
        listaRespuesta.sort(Comparator.comparing(User::getSurname).thenComparing(User::getName));
        return listaRespuesta;
    };
    public Points getPointByCoords(double horizontal, double vertical){
        logger.info("Get point by coords");
        try{
            for(Points p: points)
            {
                if(p.getHorizontal()==horizontal && p.getVertical() == vertical){
                    logger.info("Point found= "+p);
                    return p;
                }
            }
            logger.warn("Point not foud");
            throw new PointNotFoundException();
        }
        catch(PointNotFoundException exception){
            return null;
        }
    }
    public boolean RegisterInterestPoint(String id, double horizontal, double vertical){
        User u = this.getUser(id);
        Points p = this.getPointByCoords(horizontal,vertical);
        logger.info("register new point "+p+" to user "+u);
        if (u!= null && p!= null){
            this.pointsOfUser.get(u.getMail()).add(p);
            this.usersOfPoint.get(p.getId()).add(u);
            logger.info("Point=" +p+ "added to user= "+u);
            return true;
        }
        else{
            logger.warn("Attention, wrong id="+id+", or wrong coords");
            return false;
        }
    };
    public List<Points> GetListOfPointsUser(String id){
        User u = this.getUser(id);
        if(u!= null){
            logger.info("get points of user= "+u);
            return pointsOfUser.get(this.getUser(id).getMail());
        }
        else{
            logger.warn("User with id= " +id+", not found");
            return null;
        }
    };
    public List<User> GetListUsersOfPoint(double horizontal, double vertical){
        Points p = this.getPointByCoords(horizontal,vertical);
        if(p!= null){
            logger.info("get user of points= "+p);
            return usersOfPoint.get(p.getId());
        }
        else{
            logger.warn("Point with coords= " +horizontal+", "+vertical+" not found");
            return null;
        }
    };
    public List<Points> GetPointsOfType(ElementType type){
        logger.info("Get points of type ="+type);
        try{
            List<Points> answer = new ArrayList<>();
            for(Points p: points){
                if(p.getType().equals(type))
                    answer.add(p);
            }
            if(answer.size()==0){
                logger.warn("Attention, there are no points of type= "+type);
                throw new NoPointsFoundException();
            }
            else
                return answer;
        }
        catch(NoPointsFoundException ex){
            return null;
        }
    };
    public void clear(){
        logger.info("Clear all");
        points.clear();
        users.clear();
    };
    public int sizeUsers(){
        return users.size();
    };
    public int sizePoints(){
        return points.size();
    };
    public List<Points> GetAllPoints(){
      return points;
    };
}
