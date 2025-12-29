package be.wishlist.API;

import javax.ws.rs.core.MediaType;

import java.time.LocalDateTime;
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

import be.wishlist.enums.InvitationStatus;
import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.Invitation;
import be.wishlist.javabeans.InvitationDTO;
import be.wishlist.javabeans.User;

@Path("/Invitation")
public class InvitationAPI 
{
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertInvitation(String data) 
	{
		try {
			
			JSONObject json = new JSONObject(data);
			LocalDateTime sent = LocalDateTime.parse(json.getString("sent"));
			InvitationStatus status = InvitationStatus.valueOf(json.getString("status"));
			
			//Voir comment trouver le user
			User user = User.getUser(json.getInt("userid"));
			
			//modifier
			GiftList gf = new GiftList(json.getInt("giftlistid"));
			
			if( status == null || user == null || gf == null ) 
			{
				return Response
						.status(Status.BAD_REQUEST)
						.build();
			}
			
			Invitation inv = new Invitation(status, user, gf, sent);
			
			boolean ok = inv.create(inv);
			
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
		
		ArrayList<InvitationDTO> dtos = new ArrayList<>();

		for (Invitation inv : invitations) {
		    InvitationDTO dto = new InvitationDTO();
		    dto.id = inv.getId();
		    dto.status = inv.getStatus().name();
		    dto.sent = inv.getSentdate();
		    dto.userId = inv.getUser().getIdUser();
		    dto.giftListId = inv.getGiftlist().getId();
		    dtos.add(dto);
		}

		
		return Response
				.status(Status.OK)
				.entity(dtos)
				.build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/giftlist/{id}")
	public Response getGiftListInvitations(@PathParam("id") int id) 
	{
		GiftList gf = new GiftList(id);
		
		
		ArrayList<Invitation> invitations = Invitation.findGiftListInvitations(gf);
		
		if(invitations.isEmpty()) 
		{
			return Response
					.status(Status.NO_CONTENT)
					.build();
		}
		
		ArrayList<InvitationDTO> dtos = new ArrayList<>();

		for (Invitation inv : invitations) {
		    InvitationDTO dto = new InvitationDTO();
		    dto.id = inv.getId();
		    dto.status = inv.getStatus().name();
		    dto.sent = inv.getSentdate();
		    dto.userId = inv.getUser().getIdUser();
		    dto.giftListId = inv.getGiftlist().getId();
		    dtos.add(dto);
		}
		
		return Response
				.status(Status.OK)
				.entity(dtos)
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
			GiftList gf = new GiftList(json.getInt("giftlistid"));
			
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
			
			boolean ok = exist.update(exist);
			
			if(ok) 
			{
	
				return Response
						.status(Status.NO_CONTENT)
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
			
			boolean ok = exist.delete(exist);
			
			if(ok) 
			{
				return Response
						.status(Status.NO_CONTENT)
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
