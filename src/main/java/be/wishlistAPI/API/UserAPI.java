package be.wishlistAPI.API;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import be.wishlistAPI.javabeans.User;

@Path("/user")
public class UserAPI {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertUser(String data) {
		JSONObject json = new JSONObject(data);
		String firstname = json.getString("firstname");
		String lastname = json.getString("lastname");
		String username = json.getString("username");
		String password = json.getString("password");
		
		if(firstname == null || lastname == null || username == null 
				|| password == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
		User user = new User(0,firstname, lastname, username, password);
		
		boolean success = user.insert();
		if(!success) {
			
			return Response
					.status(Status.SERVICE_UNAVAILABLE)
					.build();
		}
		else {
			return Response
					.status(Status.CREATED)
					.header("Location", "/Wishlist-API/api/user/" + user.getIdUser())
					.build();
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String data) {
		
		JSONObject json = new JSONObject(data);
		String username = json.getString("username");
		String password = json.getString("password");
		
		if(username == null || password == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
		User user = User.login(username, password);
		
		if(user == null) {
			return Response
					.status(Status.UNAUTHORIZED)
					.build();
		}
		else {
			return Response
					.status(Status.OK)
					.entity(user)
					.build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getUser(@PathParam("id") int id) {
		
		User user = User.getUser(id);
		if(user == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.status(Status.OK).entity(user).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		
		ArrayList<User> users = User.getUsers();
		if(users == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.status(Status.OK).entity(users).build();
	}
	
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") int id, String data) {
		
	    JSONObject json = new JSONObject(data);
	    String firstname = json.getString("firstname");
	    String lastname = json.getString("lastname");
	    String username = json.getString("username");
	    String password = json.getString("password");

	    
	    User user = new User(id, firstname, lastname, username, password);
	    
	        if (user.update()) {
	            return Response.status(Status.NO_CONTENT).build();
	        } else {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        }
	}
	
	 @DELETE 
	    @Path("{id}")
	    public Response deleteUser(@PathParam("id") int id) {
		 
	        User user = User.getUser(id);
	        if (user != null) {
	        	if (!user.delete()) {
	        		return Response.status(Status.SERVICE_UNAVAILABLE).build();	
	             }
	        }
	        return Response.status(Status.NO_CONTENT).build();
	 }
}
