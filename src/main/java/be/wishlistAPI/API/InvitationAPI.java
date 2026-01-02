package be.wishlistAPI.API;

import javax.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import be.wishlistAPI.enums.InvitationStatus;
import be.wishlistAPI.javabeans.GiftList;
import be.wishlistAPI.javabeans.Invitation;
import be.wishlistAPI.javabeans.User;

@Path("/Invitation")
public class InvitationAPI 
{
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertInvitation(String data) 
	{
		try {
			
			JSONObject json = new JSONObject(data);
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
			LocalDateTime sent = LocalDateTime.parse(json.getString("sent"), formatter);
			InvitationStatus status = InvitationStatus.valueOf(json.getString("status"));
			
			User user = User.getUser(json.getInt("userid"));
			
			//modifier
			GiftList gf = GiftList.getGiftList(json.getInt("giftlistid"));
			
			if( status == null || user == null || gf == null ) 
			{
				return Response
						.status(Status.BAD_REQUEST)
						.build();
			}
			
			Invitation inv = new Invitation(status, user, gf, sent);
			
			boolean ok = Invitation.create(inv);
			
			if(ok) 
			{
				return Response
						.status(Status.CREATED)
						.build();
			}
			else 
			{
				return Response
						.status(Status.SERVICE_UNAVAILABLE)
						.build();
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getInvitation(@PathParam("id") int id) 
	{
		Invitation i = Invitation.find(id);
		
		if(i==null) 
		{
			return Response
					.status(Status.NOT_FOUND)
					.build();
		}
		
		return Response
				.status(Status.OK)
				.entity(i)
				.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{id}")
	public Response getUserInvitations(@PathParam("id") int id) 
	{
		User user = User.getUser(id);
		
		if (user == null) 
		{ 
			return Response
					.status(Response.Status.NOT_FOUND) 
					.build(); 
		}
		
		ArrayList<Invitation> invitations = Invitation.findUserInvitations(user);
		
		if(invitations.isEmpty()) 
		{
			return Response
					.status(Status.NO_CONTENT)
					.build();
		}

		return Response
				.status(Status.OK)
				.entity(invitations)
				.build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/giftlist/{id}")
	public Response getGiftListInvitations(@PathParam("id") int id) 
	{
		GiftList gf = GiftList.getGiftList(id);
		
		
		ArrayList<Invitation> invitations = Invitation.findGiftListInvitations(gf);
		
		if(invitations.isEmpty()) 
		{
			return Response
					.status(Status.NO_CONTENT)
					.build();
		}
		
		return Response
				.status(Status.OK)
				.entity(invitations)
				.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/accepted/{id}")
	public Response getInvitedGiftlist(@PathParam("id") int id) 
	{
		ArrayList<GiftList> gf = Invitation.getInvitedGiftlist(id);
		
		if(gf.isEmpty() || gf==null) 
		{
			return Response
					.status(Status.NO_CONTENT)
					.build();
		}
		
		return Response
		.status(Status.OK)
		.entity(gf)
		.build();
	}
	
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateInvitation(@PathParam("id") int id, String data) 
	{
		
		
		try {
			Invitation exist = Invitation.find(id);
			
			if (exist == null) 
			{ 
				return Response
						.status(Status.NOT_FOUND)
						.build();
			}
			
			JSONObject json = new JSONObject(data);
			LocalDateTime sent = LocalDateTime.parse(json.getString("sent"));
			InvitationStatus status = InvitationStatus.valueOf(json.getString("status"));
			
			//Voir comment trouver le user
			User user = User.getUser(json.getInt("userid"));
			
			//modifier
			GiftList gf = GiftList.getGiftList(json.getInt("giftlistid"));
			
			if( status == null || user == null || gf == null ) 
			{
				return Response
						.status(Status.BAD_REQUEST)
						.build();
			}
			
			exist.setSentdate(sent); 
			exist.setStatus(status); 
			exist.setUser(user); 
			exist.setGiftlist(gf);
			
			boolean ok = Invitation.update(exist);
			
			if(ok) 
			{
	
				return Response
						.status(Status.OK)
						.build();
			}
			else 
			{
				return Response
						.status(Status.BAD_REQUEST)
						.build();
			}
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.build();
			
		}
	}
	
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteInvitation(@PathParam("id") int id) 
	{
		try 
		{
			Invitation exist = Invitation.find(id);
			
			if (exist == null) 
			{ 
				return Response
						.status(Status.NOT_FOUND)
						.build();
			}
			
			boolean ok = Invitation.delete(exist);
			
			if(ok) 
			{
				return Response
						.status(Status.OK)
						.build();
			}
			else 
			{
				return Response
						.status(Status.BAD_REQUEST)
						.build();
			}
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	
}
