package be.wishlistAPI.DAO;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import be.wishlistAPI.Connection.DatabaseConnection;
import be.wishlistAPI.enums.GiftListStatus;
import be.wishlistAPI.enums.InvitationStatus;
import be.wishlistAPI.javabeans.GiftList;
import be.wishlistAPI.javabeans.Invitation;
import be.wishlistAPI.javabeans.User;


public class InvitationDAO extends DAO<Invitation> {
	
	public InvitationDAO(Connection conn) {
		super(conn);
	}
	
	//fait
	@Override
	public boolean create(Invitation obj) {
		
		String query = "{call INSERT_INVITATION(?,?,?,?,?,?)}";
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setTimestamp(1, Timestamp.valueOf(obj.getSentdate()));
			cs.setString(2, obj.getStatus().name());
			cs.setInt(3, obj.getUser().getIdUser());
			cs.setInt(4, obj.getGiftlist().getIdgiftlist());
			
			cs.registerOutParameter(5, Types.INTEGER);
			cs.registerOutParameter(6, Types.INTEGER);
			
			cs.execute();
			
			int updated = cs.getInt(5);
			int id = cs.getInt(6);
			obj.setId(id);
			
			return updated == 1;
		}
		catch(SQLException e) 
		{
			System.out.println("Erreur dans create invitationDAO");
			e.printStackTrace();
		}
		
		return false;
	}
	
	//fait
	@Override
	public boolean delete(Invitation obj) {
		
		String query = "{call DELETE_INVITATION(?,?)}";
		
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
			System.out.println("Erreur dans delete invitationDAO");
			e.printStackTrace();
		}
		return false;
	}
	
	//fait
	@Override
	public boolean update(Invitation obj) {
		
		String query = "{call UPDATE_INVITATION(?,?,?)}";
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setInt(1, obj.getId());
			cs.setString(2, obj.getStatus().name());
			
			cs.registerOutParameter(3, Types.INTEGER);
			
			cs.execute();
			
			int updated = cs.getInt(3);
			
			return updated == 1;
		}
		catch(SQLException e) 
		{
			System.out.println("Erreur dans update invitationDAO");
			e.printStackTrace();
		}
		
		return false;
	}
	//fait
	@Override
	public Invitation find(int id) {
		
		String query = "{call GET_INVITATION(?,?,?,?,?)}";
		
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setInt(1, id);
			cs.registerOutParameter(2, Types.TIMESTAMP);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.registerOutParameter(5, Types.INTEGER);
			
			cs.execute();
			
			InvitationStatus status = InvitationStatus.valueOf(cs.getString(3));
			UserDAO ud = new UserDAO(connect);
			GiftListDAO gd = new GiftListDAO(connect);
			
			
			User user = ud.find(cs.getInt(4));
			GiftList giftlist = gd.find(cs.getInt(5));
			Timestamp ts = cs.getTimestamp(2);
			LocalDateTime sent = ts.toLocalDateTime();
			
			return new Invitation(id, status, user,giftlist,sent);
		}
		catch(SQLException e) 
		{
			System.out.println("Erreur dans find invitationDAO");
			e.printStackTrace();
		}
		
		return null;
	}

	//fait
	public ArrayList<Invitation> findUserInvitations(User user)
	{
		String query = "{call findUserInvitation(?,?)}";
		ArrayList<Invitation> inv = new ArrayList<Invitation>();
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.registerOutParameter(1, Types.REF_CURSOR);
			cs.setInt(2, user.getIdUser());
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(1);
			
			while(rs.next()) 
			{
				int id = rs.getInt("id_invitation");
				String stat = rs.getString("status");
				Timestamp ts = rs.getTimestamp("sent");
				int listid = rs.getInt("id_giftlist");
				LocalDateTime sent = ts.toLocalDateTime();
				InvitationStatus status = InvitationStatus.valueOf(stat);
				
				GiftList gf = GiftList.getGiftList(listid);
				
				Invitation i = new Invitation(id, status, user, gf,sent);
				inv.add(i);
			}
			return inv;
		}
		catch(SQLException e) 
		{
			System.out.println("Erreur dans finduserinvitations invitationdao");
			e.printStackTrace();
		}
		return inv;
	}
	
	//fait
	public int getInvitationId(Invitation obj) 
	{
		String query = "{call getInvitationId(?,?,?,?,?)}";
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setString(1, obj.getStatus().name());
			cs.setTimestamp(2, Timestamp.valueOf(obj.getSentdate()));
			cs.setInt(3, obj.getUser().getIdUser());
			cs.setInt(4, obj.getGiftlist().getIdgiftlist());
			cs.registerOutParameter(5, Types.INTEGER);
			
			cs.execute();
			
			return cs.getInt(5);
		}
		catch(SQLException e) 
		{
			System.out.println("Erreur dans getinvitationid invitationDAO");
			e.printStackTrace();
		}
		return -1;
		
	}
	
	public ArrayList<Invitation> findGiftListInvitation(GiftList gf)
	{
		String query = "{call findgiftlistinvitation(?,?)}";
		ArrayList<Invitation> inv = new ArrayList<Invitation>();
		
		try(CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.registerOutParameter(1, Types.REF_CURSOR);
			cs.setInt(2, gf.getIdgiftlist());
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(1);
			
			while(rs.next()) 
			{
				int id = rs.getInt("id_invitation");
				String stat = rs.getString("status");
				Timestamp ts = rs.getTimestamp("sent");
				int userid = rs.getInt("id_user");
				LocalDateTime sent = ts.toLocalDateTime();
				InvitationStatus status = InvitationStatus.valueOf(stat);
				
				User user = User.getUser(userid);
				
				Invitation i = new Invitation(id,status,user,gf,sent);
				inv.add(i);
			}
			return inv;
		}
		catch(SQLException e) 
		{
			System.out.println("Erreur dans getgiftlistinvitation invitationdao");
			e.printStackTrace();
		}
		return inv;
	}

	@Override
	public ArrayList<Invitation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Invitation> findAll(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<GiftList> getInvitedGiftlist(int id)
	{
		String query = "{call getAcceptedInvitations(?,?)}";
		ArrayList<GiftList> gls = new ArrayList<GiftList>();
		
		try (CallableStatement cs = this.connect.prepareCall(query))
		{
			cs.setInt(1, id);
			cs.registerOutParameter(2, Types.REF_CURSOR);
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(2);
			
			while(rs.next()) 
			{
				int idgiftlist = rs.getInt("id_giftlist");
	            String title = rs.getString("title");
	            String description = rs.getString("description");

	            LocalDate creationdate = rs.getDate("creationdate").toLocalDate();
	            LocalDate expirationdate = rs.getDate("expirationdate").toLocalDate();

	            GiftListStatus status = GiftListStatus.valueOf(rs.getString("status"));

	            String sharelink = rs.getString("sharelink");

	            User owner = User.getUser(rs.getInt("id_user"));

	            GiftList gl = new GiftList(idgiftlist,title,description,creationdate,
	                expirationdate,status,sharelink,owner);

	            gls.add(gl);
			}
			return gls;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
}

