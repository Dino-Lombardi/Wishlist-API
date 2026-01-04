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

import be.wishlistAPI.enums.GiftStatus;
import be.wishlistAPI.javabeans.Gift;
import be.wishlistAPI.javabeans.GiftList;

@Path("/gift")
public class GiftAPI {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertGift(String data) {
	    try {
	        JSONObject json = new JSONObject(data);

	        Gift gift = new Gift();

	        gift.setName(json.getString("name"));
	        gift.setDescription(json.optString("description", null));
	        gift.setPrice(json.getDouble("price"));
	        gift.setPriority(json.getInt("priority"));
	        gift.setStatus(GiftStatus.valueOf(json.optString("status", "AVAILABLE")));
	        gift.setBuylink(json.optString("buylink", null));

	        gift.setImage(json.optString("image", null));

	        GiftList giftlist = new GiftList();
	        giftlist.setIdgiftlist(json.getInt("idgiftlist"));
	        gift.setGiftlist(giftlist);

	        boolean success = gift.insert();

	        if(!success) {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        }

	        return Response.status(Status.CREATED)
	                .header("Location", "/Wishlist-API/api/gift/" + giftlist.getIdgiftlist())
	                .build();

	    } catch(Exception e) {
	        e.printStackTrace();
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getGift(@PathParam("id") int id) {
		
		Gift gift = Gift.getGift(id);
		
		if(gift == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		
		return Response.status(Status.OK).entity(gift).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/giftlist/{idgiftlist}")
	public Response getGiftsFromGiftList(@PathParam("idgiftlist") int idgiftlist) {
	
		ArrayList<Gift> gifts = Gift.getGiftsByGiftList(idgiftlist);
		if(gifts == null) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.status(Status.OK).entity(gifts).build();
	}
	
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateGift(@PathParam("id") int id, String data) {
		
	    JSONObject json = new JSONObject(data);
	    String name;
		String description = json.optString("description", null);
		double price;
		int priority;
		String status = json.optString("status", "AVAILABLE");
		String buylink = json.optString("buylink", null);
		String image = json.optString("image", null);
		int idgiftlist;
		
		Gift gift = new Gift();
		try {
			name = json.getString("name");
			price = json.getDouble("price");
			priority = json.getInt("priority");
			idgiftlist = json.getInt("idgiftlist");
			gift.setStatus(GiftStatus.valueOf(status));
		} catch (Exception e) {
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
		
		gift.setIdGift(id);
		gift.setName(name);
		gift.setDescription(description);
		gift.setPrice(price);
		gift.setPriority(priority);
		gift.setBuylink(buylink);
		GiftList giftlist = new GiftList();
		giftlist.setIdgiftlist(idgiftlist);
		gift.setGiftlist(giftlist);
		gift.setImage(image);

		
	        if (gift.update()) {
	            return Response.status(Status.OK).build();
	        } else {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        }
	}
	
	 @DELETE 
	 @Path("{id}")
	 public Response deleteGift(@PathParam("id") int id) {
		 
	        Gift gift = Gift.getGift(id);
	        if (gift != null) {
	        	if (!gift.delete()) {
	        		return Response.status(Status.SERVICE_UNAVAILABLE).build();	
	             }
	        }
	        
	        return Response.status(Status.OK).build();
	 }
}
