package ecommerce;

import ecommerce.User;
import ecommerce.UserService;
import jakarta.servlet.UnavailableException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/users")
public class UserResource {


    @Path("{username}")
    @GET
    @Produces( { MediaType.APPLICATION_JSON }) //This provides only JSON responses
    public Response getUserByUserName(@PathParam("username") String un/* The {username} placeholder parameter is resolved */) throws UnavailableException {
        //invokes the DB method which will fetch a user item object by id
        User user = UserService.getUserByUserName(un);

        //Respond with a 404 if there is no such user for the id provided
        if(user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Respond with a 200 OK if you have a user object to return as response
        return Response.ok(user).build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON}) //This method accepts a request of the JSON type
    public Response addUser(User user) throws UnavailableException {

        //The user object here is automatically constructed from the json request. Jersey is so cool!
        if(UserService.AddUser(user)) {
            return Response.ok().entity("User Added Successfully").build();
        }

        // Return an Internal Server error because something wrong happened. This should never be executed
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    }
}
