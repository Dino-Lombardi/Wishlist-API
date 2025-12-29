package be.wishlist.API;

import java.time.LocalDate;
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

import be.wishlist.javabeans.Gift;
import be.wishlist.javabeans.Reservation;
import be.wishlist.javabeans.User;

@Path("/Reservation")
public class ReservationAPI 
{
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertReservation(String data) 
	{
		try 
		{
			JSONObject json = new JSONObject(data);
			LocalDate date = LocalDate.parse(json.getString("ReservationDate"));
			User user = User.getUser(json.getInt("userid"));
			Gift gf = new Gift(json.getInt("giftid"));
			Double amount = json.getDouble("amount");
			boolean isgroup = json.getBoolean("isgroup");
			
			if(date == null || user == null || gf == null || amount == null) 
			{
				return Response
						.status(Status.BAD_REQUEST)
						.build();
			}
			
			Reservation r = new Reservation(date,amount,isgroup,user,gf);
			
			boolean ok = Reservation.create(r);
			
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
	public Response getUserReservation(@PathParam("id") int id) 
	{
		User user = User.getUser(id);
		
		if (user == null) 
		{ 
			return Response
					.status(Response.Status.NOT_FOUND) 
					.build(); 
		}
		
		ArrayList<Reservation> res = Reservation.findUserReservations(user);
		
		if(res.isEmpty()) 
		{
			return Response
					.status(Status.NO_CONTENT)
					.build();
		}
		
		return Response
				.status(Status.OK)
				.entity(res)
				.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gift/{id}")
	public Response getGiftListInvitations(@PathParam("id") int id) 
	{
		Gift gf = new Gift(id);
		
		ArrayList<Reservation> res = Reservation.findGiftReservations(gf);
		
		if(res.isEmpty()) 
		{
			return Response
					.status(Status.NO_CONTENT)
					.build();
		}
		
		return Response
				.status(Status.OK)
				.entity(res)
				.build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateReservation(@PathParam("id") int id, String data) 
	{
		try 
		{
			JSONObject json = new JSONObject(data);
			LocalDate date = LocalDate.parse(json.getString("ReservationDate"));
			User user = User.getUser(json.getInt("userid"));
			Gift gf = new Gift(json.getInt("giftid"));
			Double amount = json.getDouble("amount");
			boolean isgroup = json.getBoolean("isgroup");
			
			if(date == null || user == null || gf == null || amount == null) 
			{
				return Response
						.status(Status.BAD_REQUEST)
						.build();
			}
			
			Reservation r = new Reservation(id,date,amount,isgroup,user,gf);
			
			boolean ok = Reservation.update(r);
			
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
	
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteReservation(@PathParam("id") int id) 
	{
		try 
		{
			Reservation r = new Reservation(id);
			
			boolean ok = Reservation.delete(r);
			
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
}
