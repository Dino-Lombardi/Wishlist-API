package be.wishlistAPI.DAO;

import java.awt.Window.Type;
import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

import be.wishlistAPI.javabeans.Gift;
import be.wishlistAPI.javabeans.Reservation;
import be.wishlistAPI.javabeans.User;

public class ReservationDAO extends DAO<Reservation>
{

	public ReservationDAO(Connection conn) {
		super(conn);
		
	}
	//fait
	@Override
	public boolean create(Reservation obj) {
		
		String query = "{call INSERT_RESERVATION(?,?,?,?,?,?)}";
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setDate(1, java.sql.Date.valueOf(obj.getReservationDate()));
			cs.setDouble(2, obj.getAmount());
			cs.setInt(3, obj.isIsgrouppurchase() ? 1 : 0);
			cs.setInt(4, obj.getUser().getIdUser());
			cs.setInt(5, obj.getGift().getIdGift());
			cs.registerOutParameter(6, Types.INTEGER);
			
			cs.execute();
			
			int updated = cs.getInt(6);
			return updated == 1;
			
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Erreur dans reservation DAO create");
		}
		
		
		return false;
	}
	//fait
	@Override
	public Reservation find(int id) {
		
		String query = "{call find_RESERVATION(?,?,?,?,?,?)}";
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setInt(1, id);
			cs.registerOutParameter(2, Types.DATE);
			cs.registerOutParameter(3, Types.DOUBLE);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.registerOutParameter(6, Types.INTEGER);
			
			cs.execute();
			
			User user = User.getUser(cs.getInt(5));
			//TODO
			Gift gift = Gift.getGift(cs.getInt(6));
			boolean isGroup = cs.getInt(4) == 1;
			LocalDate reserved = cs.getDate(2).toLocalDate();
			Reservation r = new Reservation(id, reserved, cs.getDouble(3), isGroup, user,gift);
			
			return r;
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Erreur dans reservation DAO find");
		}
		
		return null;
	}

	@Override
	public ArrayList<Reservation> findAll() {
		return null;
	}
	//fait
	@Override
	public boolean update(Reservation obj) {
		String query = "{call UPDATE_RESERVATION(?,?,?,?,?)}";
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setInt(1, obj.getId());
			cs.setDate(2, java.sql.Date.valueOf(obj.getReservationDate()));
			cs.setDouble(3, obj.getAmount());
			cs.setInt(4, obj.isIsgrouppurchase() ? 1 : 0);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			
			int updated = cs.getInt(5);
			
			return updated == 1;
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Erreur dans reservation DAO update");
		}
		
		return false;
	}
	
	//fait
	@Override
	public boolean delete(Reservation obj) {
		String query = "{call DELETE_RESERVATION(?,?)}";
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setInt(1, obj.getId());
			cs.registerOutParameter(2, Types.INTEGER);
			
			cs.execute();
			
			int updated = cs.getInt(2);
			
			return updated == 1;
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Erreur dans reservation DAO delete");
		}
		
		return false;
	}
	//fait
	public ArrayList<Reservation> findUserReservations(User user)
	{
		ArrayList<Reservation> res = new ArrayList<Reservation>();
		String query = "{call findUserReservation(?,?)}";
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setInt(1, user.getIdUser());
			cs.registerOutParameter(2, Types.REF_CURSOR);
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(2);
			
			while(rs.next()) 
			{
				LocalDate date = rs.getDate("reservation_date").toLocalDate();
				int id = rs.getInt("id_reservation");
				Double amount = rs.getDouble("amount");
				boolean b = rs.getBoolean("is_group_purchase");
				Gift g = Gift.getGift(rs.getInt("id_gift"));
				Reservation r = new Reservation(id,date, amount, b, user, g);
				res.add(r);
			}
			
			return res;
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Erreur dans finduserreservation reservation DAO");
		}
		return res;
	}
	//fait
	public ArrayList<Reservation> findGiftReservations(Gift gift)
	{
		ArrayList<Reservation> res = new ArrayList<Reservation>();
		String query = "{call findGiftReservation(?,?)}";
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setInt(1, gift.getIdGift());
			cs.registerOutParameter(2, Types.REF_CURSOR);
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(2);
			
			while(rs.next()) 
			{
				LocalDate date = rs.getDate("RESERVATION_DATE").toLocalDate();
				int id = rs.getInt("ID_RESERVATION");
				double amount = rs.getDouble("AMOUNT");
				boolean b = rs.getInt("IS_GROUP_PURCHASE") == 1;
				User user = User.getUser(rs.getInt("ID_USER"));
				Reservation r = new Reservation(id,date, amount, b, user, gift);
				res.add(r);
			}
			
			return res;
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Erreur dans findgiftreservation reservation DAO");
		}
		return res;
	}
	@Override
	public ArrayList<Reservation> findAll(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
