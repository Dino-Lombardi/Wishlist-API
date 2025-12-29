package be.wishlistAPI.API;

import java.time.LocalDate;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import be.wishlistAPI.enums.GiftListStatus;
import be.wishlistAPI.javabeans.GiftList;
import be.wishlistAPI.javabeans.User;

@Path("/giftlist")
public class GiftListAPI {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertGiftList(String data) {
		JSONObject json = new JSONObject(data);
		String title = json.getString("title");
		String description = json.optString("description", null);
		String expirationdate = json.getString("expirationdate");
		String status = json.optString("status", null);
		String sharelink = json.optString("sharelink", null);
		
		try {
			 title = json.getString("title");
			 expirationdate = json.getString("expirationdate");
		} catch (Exception e) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
		int idowner = json.getInt("idowner");
		
		
		if(title == null || expirationdate == null || status == null ) 
		{
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
		GiftList giftlist = new GiftList();
		giftlist.setTitle(title);
		giftlist.setDescription(description);
		DateTimeFormatter frenchFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		giftlist.setExpirationdate(LocalDate.parse(expirationdate, frenchFormatter));		
		giftlist.setStatus(GiftListStatus.valueOf(status));
		giftlist.setSharelink(sharelink);
		User owner = new User();
		owner.setIdUser(idowner);
		giftlist.setOwner(owner);
		
		
		boolean success = giftlist.insert();
		if(!success) {
			
			return Response
					.status(Status.SERVICE_UNAVAILABLE)
					.build();
		}
		else {
			return Response
					.status(Status.CREATED)
					.header("Location", "/Wishlist-API/api/giftlist/" + giftlist.getIdgiftlist())
					.build();
		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getGiftList(@PathParam("id") int id) {
		
		GiftList giftlist = GiftList.getGiftList(id);
		if(giftlist == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.status(Status.OK).entity(giftlist).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{iduser}")
	public Response getGiftListsFromUser(@PathParam("iduser") int iduser) {
		
		ArrayList<GiftList> giftlists = GiftList.getGiftListsByUser(iduser);
		if(giftlists == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.status(Status.OK).entity(giftlists).build();
	}
	
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateGiftList(@PathParam("id") int id, String data) {
		
	    JSONObject json = new JSONObject(data);
	    String title = json.getString("title");
		String description = json.getString("description");
		String expirationdate = json.getString("expirationdate");
		String status = json.getString("status");
		String sharelink = json.getString("sharelink");

	    
		GiftList giftlist = new GiftList();
		giftlist.setTitle(title);
		giftlist.setDescription(description);
		DateTimeFormatter frenchFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		giftlist.setExpirationdate(LocalDate.parse(expirationdate, frenchFormatter));
		giftlist.setStatus(GiftListStatus.valueOf(status));
		giftlist.setSharelink(sharelink);
		
	        if (giftlist.update()) {
	            return Response.status(Status.NO_CONTENT).build();
	        } else {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        }
	}
	
	 @DELETE 
	 @Path("{id}")
	 public Response deleteGiftList(@PathParam("id") int id) {
		 
	        GiftList giftlist = GiftList.getGiftList(id);
	        if (giftlist != null) {
	        	if (!giftlist.delete()) {
	        		return Response.status(Status.SERVICE_UNAVAILABLE).build();	
	             }
	        }
	        return Response.status(Status.NO_CONTENT).build();
	 }
}
