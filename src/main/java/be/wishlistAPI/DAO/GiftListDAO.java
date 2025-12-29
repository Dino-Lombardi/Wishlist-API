package be.wishlistAPI.DAO;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import be.wishlistAPI.enums.GiftListStatus;
import be.wishlistAPI.javabeans.GiftList;
import be.wishlistAPI.javabeans.User;
import oracle.jdbc.OracleTypes;

public class GiftListDAO extends DAO<GiftList> {
	
	public GiftListDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(GiftList obj) {
		boolean success = false;
		
		String query = "{call INSERT_GIFTLIST(?, ?, ?, ?, ?, ?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setString(1, obj.getTitle());
			cs.setString(2, obj.getDescription());
			cs.setDate(3, Date.valueOf(obj.getExpirationdate()));
			cs.setString(4, obj.getStatus().toString());
			cs.setString(5, obj.getSharelink());
			cs.setInt(6, obj.getOwner().getIdUser());
			
			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		return success;
	}
	
	@Override
	public GiftList find(int id) {
		GiftList giftlist = null;
	       
		String query = "{? = call SELECT_GIFTLIST(?)}";
		 
		try (CallableStatement cs = this.connect.prepareCall(query)) { 
			cs.registerOutParameter(1, OracleTypes.STRUCT, "TYP_GIFTLIST");
			cs.setInt(2, id); 
			cs.executeQuery();
	            
			Object data = (Object) cs.getObject(1); 
			if (data != null) {
				Struct row = (Struct)data; 
				Object[] values = (Object[]) row.getAttributes();
				String title = String.valueOf(values[1]); 
				String description = String.valueOf(values[2]);	           
				LocalDate creationdate = ((Timestamp)values[3]).toLocalDateTime().toLocalDate();
				LocalDate expirationdate = ((Timestamp)values[4]).toLocalDateTime().toLocalDate();
				GiftListStatus status = GiftListStatus.valueOf(String.valueOf(values[5]));
				String sharelink = String.valueOf(values[6]);
				User owner = new User();
				owner.setIdUser(Integer.parseInt(String.valueOf(values[7])));
				
		            
				giftlist = new GiftList(id, title, description, creationdate, expirationdate, status, sharelink, owner);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return giftlist;
	}
	
	@Override
	public ArrayList<GiftList> findAll(int iduser) {
		
		ArrayList<GiftList> giftlists = new ArrayList<GiftList>();

		String query = "{? = call SELECT_GIFTLISTS_FROM_USER(?)}";

		try (CallableStatement cs = this.connect.prepareCall(query)) {
			
			cs.registerOutParameter(1, OracleTypes.ARRAY, "TYP_TAB_GIFTLIST");
			cs.setInt(2, iduser);
			cs.executeQuery();
			
			Array arr = cs.getArray(1); 
			if (arr != null) {
				Object[] data = (Object[]) arr.getArray(); 
				for (Object a : data) {
				    Struct row = (Struct) a;
				    Object[] values = (Object[]) row.getAttributes();
				    int id = Integer.valueOf(String.valueOf(values[0]));
				    String title = String.valueOf(values[1]); 
					String description = String.valueOf(values[2]);	           
					LocalDate creationdate = ((Timestamp)values[3]).toLocalDateTime().toLocalDate();
					LocalDate expirationdate = ((Timestamp)values[4]).toLocalDateTime().toLocalDate();
					GiftListStatus status = GiftListStatus.valueOf(String.valueOf(values[5]));
					String sharelink = String.valueOf(values[6]);
					User owner = new User();
					owner.setIdUser(Integer.parseInt(String.valueOf(values[7])));
					    
					GiftList giftlist = new GiftList(id, title, description, creationdate, expirationdate, status, sharelink, owner);
					giftlists.add(giftlist);
				}
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} 
		return giftlists;
	}

	

	@Override
	public boolean update(GiftList obj) {
		boolean success = false;
		
		String query = "{call UPDATE_USER(?, ?, ?, ?, ?, ?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setInt(1, obj.getIdgiftlist());
			cs.setString(2,  obj.getTitle());
			cs.setString(3,  obj.getDescription());
			cs.setDate(4, Date.valueOf(obj.getExpirationdate()));
			cs.setString(5,  obj.getStatus().toString());
			cs.setString(6,  obj.getSharelink());
			
			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return success;
	}
	
	@Override
	public boolean delete(GiftList obj) {
		boolean success = false;
		
		String query = "{call DELETE_GIFTLIST(?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setInt(1, obj.getIdgiftlist());
			
			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return success;
	}

	@Override
	public ArrayList<GiftList> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
