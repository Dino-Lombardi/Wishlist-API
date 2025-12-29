package be.wishlist.DAO;

import java.sql.Connection;

import be.wishlist.Connection.DatabaseConnection;
import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.Invitation;
import be.wishlist.javabeans.Reservation;
import be.wishlist.javabeans.User;

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
	public DAO<Reservation> getReservationDAO()
	{
		return new ReservationDAO(conn);
	}
	
}
