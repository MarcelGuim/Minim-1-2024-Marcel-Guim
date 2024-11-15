package edu.upc.dsa.services;

import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.UserManager;
import edu.upc.dsa.exceptions.WrongTypeException;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/users", description = "Endpoint to Users Service")
@Path("/users")
public class UsersService {

    private UserManager Um;

    public UsersService() {
        this.Um = UserManagerImpl.getInstance();
        if (Um.sizeUsers()==0) {
            String dataString1 = "11/04/2003";
            String dataString2 = "11/04/2003";
            String dataString3 = "11/04/2003";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date1 = LocalDate.parse(dataString1, formatter);
            LocalDate date2 = LocalDate.parse(dataString2, formatter);
            LocalDate date3 = LocalDate.parse(dataString3, formatter);
            User user1 = new User("Marcel", "Guim", "Marcel", date1);
            this.Um.addUser(user1);
            this.Um.addUser("Manel", "Alehop", "Manel", date2);
            this.Um.addUser("Samuel", "Guim", "Samuel", date3);
            this.Um.addPoint(10,10,ElementType.DOOR);
            this.Um.addPoint(20,20,ElementType.WALL);
            this.Um.addPoint(20,10,ElementType.BRIDGE);
            this.Um.RegisterInterestPoint(user1.getId(), 10,10);
        }
    }

    @GET
    @ApiOperation(value = "get all Users Ordered", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        List<User> users = this.Um.GetOrderedUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all Points", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Points.class, responseContainer="List"),
    })
    @Path("/points")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPoints() {
        List<Points> points = this.Um.GetAllPoints();
        GenericEntity<List<Points>> entity = new GenericEntity<List<Points>>(points) {};
        return Response.status(201).entity(entity).build();
    }
    @POST
    @ApiOperation(value = "Add a new User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/User")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User u) {
        if (u.getName() == null || u.getSurname() == null || u.getMail() == null) return Response.status(500).entity(u).build();
        if(u.getId() == null || u.getId().equals("string")) u.setRandomId();
        User u1 = this.Um.addUser(u);
        if (u1 == null) return Response.status(500).entity(u).build();
        else return Response.status(201).entity(u1).build();
    }

    @POST
    @ApiOperation(value = "Add a new Point", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Points.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/Point")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddPoint (Points p) {
        if(p.getType()==null) return Response.status(500).entity(p).build();
        try{
            if(p.getId() == null || p.getId().equals("string")) p.setRandomId();
            this.Um.addPoint(new Points(p.getId(), p.getHorizontal(),p.getVertical(),p.getType()));
            return Response.status(201).entity(p).build();
        }
        catch (WrongTypeException ex) {
            return Response.status(500).entity(p).build();
        }
    }

    @GET
    @ApiOperation(value = "get a User With Id", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/userWithId/{id}")
    public Response GetUserWithId(@PathParam("id") String id) {
        if (id==null) return Response.status(500).build();
        if (this.Um.getUser(id) == null) return Response.status(500).build();
        else return Response.status(201).entity(this.Um.getUser(id)).build();
    }

    @POST
    @ApiOperation(value = "Add an interest point to a user (response is the list of points of the user)", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Points.class , responseContainer="List"),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/pointToUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddPointToUser(Mix m) {
        if(m.getU().getId()==null) return Response.status(500).entity(m.getP()).build();
        boolean yes = this.Um.RegisterInterestPoint(m.getU().getId(),m.getP().getHorizontal(),m.getP().getVertical());
        if (!yes) return Response.status(500).entity(m.getP()).build();
        List<Points> points = this.Um.GetListOfPointsUser(m.getU().getId());
        GenericEntity<List<Points>> entity = new GenericEntity<List<Points>>(points) {};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get the Points of a User With Id of the user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Points.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/PointsOfUser/{id}")
    public Response GetPointsWithId(@PathParam("id") String id) {
        if (id==null) return Response.status(404).build();
        List<Points> points = this.Um.GetListOfPointsUser(id);
        GenericEntity<List<Points>> entity = new GenericEntity<List<Points>>(points) {};
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "Get the users of a point", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/GetUsersOfPoint")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddPointToUser(Points p) {
        List<User> users = this.Um.GetListUsersOfPoint(p.getHorizontal(),p.getVertical());
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build();

    }

    @GET
    @ApiOperation(value = "get the points of a Type", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Points.class, responseContainer="List"),

            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/pointsoftype/{id}")
    public Response deleteTrack(@PathParam("id") ElementType type) {
        if (type==null) return Response.status(404).build();
        List<Points> points = this.Um.GetPointsOfType(type);
        GenericEntity<List<Points>> entity = new GenericEntity<List<Points>>(points) {};
        return Response.status(201).entity(entity).build();
    }
}
