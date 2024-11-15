package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Points;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserManagerTest {
    UserManager Um;
    //Les dates les utilitzen els tests, per tant, son variables globals
    String dataString1 = "11/04/2003";
    String dataString2 = "11/04/2003";
    String dataString3 = "11/04/2003";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate date1 = LocalDate.parse(dataString1, formatter);
    LocalDate date2 = LocalDate.parse(dataString2, formatter);
    LocalDate date3 = LocalDate.parse(dataString3, formatter);
    @Before
    public void setUp() {
        this.Um = UserManagerImpl.getInstance();
        this.Um.addUser("Marcel", "Guim", "Marcel", date1);
        this.Um.addUser("Manel", "Alehop", "Manel", date2);
        this.Um.addUser("Samuel", "Guim", "Samuel", date3);
        this.Um.addPoint(10,10,"DOOR");
        this.Um.addPoint(20,20,"WALL");
        this.Um.addPoint(20,10,"BRIDGE");
    }

    @After
    public void tearDown() {
        this.Um.clear();
    }

    @Test
    public void addUserTest() {
        Assert.assertEquals(3, Um.sizeUsers());
        this.Um.addUser("Samuel", "Guim", "Samuel", date3);
        Assert.assertEquals(3, Um.sizeUsers());
        this.Um.addUser("Marco", "Polo", "marco", date3);
        Assert.assertEquals(4, Um.sizeUsers());
    }

    @Test
    public void InfoUserWithIDTest(){
        User user1 = new User("Carles","Porta","carles",date1);
        this.Um.addUser(user1);
        User user2 = this.Um.getUser(user1.getId());
        Assert.assertSame(user1,user2);
    }

    @Test
    public void RegisterInteresPoint(){
        User user1 = new User("Carles","Porta","carles",date1);
        this.Um.addUser(user1);
        this.Um.RegisterInterestPoint(user1.getId(), 10,10);
        Assert.assertEquals(1, Um.GetListOfPointsUser(user1.getId()).size());
        Assert.assertEquals(1,Um.getPointByCoords(10,10).getUserList().size());
    }


    @Test
    public void TryWrongTypeTest(){
        Assert.assertEquals(3, Um.sizePoints());
        this.Um.addPoint(50,50,"TABLE");
        Assert.assertEquals(3, Um.sizePoints());
    }

    @Test
    public void FindPointsByTypeTest(){
        try{
            Points p1 = new Points(250,431,"SWORD");
            this.Um.addPoint(p1);
            Points p2 = this.Um.GetPointsOfType("SWORD").get(0);
            Assert.assertSame(p1,p2);
        }
        catch(WrongTypeException ex){
        }
    }
}
