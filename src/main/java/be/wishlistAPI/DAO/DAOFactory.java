package be.wishlistAPI.DAO;

import java.sql.Connection;

import be.wishlistAPI.Connection.DatabaseConnection;
import be.wishlistAPI.javabeans.Gift;
import be.wishlistAPI.javabeans.GiftList;
import be.wishlistAPI.javabeans.Invitation;
import be.wishlistAPI.javabeans.Reservation;
import be.wishlistAPI.javabeans.User;

public class DAOFactory extends AbstractDAOFactory {

	private Connection conn = DatabaseConnection.getInstance();
	
	@Override
	public DAO<User> getUserDAO() {
		return new UserDAO(conn);
	}

	@Override
	public DAO<Invitation> getInvitationDAO() {
		return new InvitationDAO(conn);
	}

	@Override
	public DAO<GiftList> getGiftListDAO() {
		return new GiftListDAO(conn);
	}
	
	@Override
	public DAO<Gift> getGiftDAO(){
		return new GiftDAO(conn);
	}

	@Override
	public DAO<Reservation> getReservationDAO()
	{
		return new ReservationDAO(conn);
	}
	
}
