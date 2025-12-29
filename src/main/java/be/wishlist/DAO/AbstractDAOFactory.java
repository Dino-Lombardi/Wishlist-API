package be.wishlist.DAO;

import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.Invitation;
import be.wishlist.javabeans.Reservation;
import be.wishlist.javabeans.User;

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
