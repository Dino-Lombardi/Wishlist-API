package be.wishlistAPI.DAO;


import be.wishlistAPI.javabeans.GiftList;
import be.wishlistAPI.javabeans.Invitation;
import be.wishlistAPI.javabeans.Reservation;
import be.wishlistAPI.javabeans.User;

public abstract class AbstractDAOFactory {
	
	public static final int DAO_FACTORY = 0;
	public static final int XML_DAO_FACTORY = 1;
	
	public abstract DAO<User> getUserDAO();
	
	public abstract DAO<Invitation> getInvitationDAO();
	
	public abstract DAO<GiftList> getGiftListDAO();
	
	public abstract DAO<Reservation> getReservationDAO();
	
	public static AbstractDAOFactory getFactory(int type){
	    switch(type){
	      case DAO_FACTORY:
	        return new DAOFactory();
	      case XML_DAO_FACTORY: 
	        return null;
	      default:
	        return null;
	    }
	}
}
