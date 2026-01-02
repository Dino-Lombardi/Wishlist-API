package be.wishlistAPI.DAO;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import be.wishlistAPI.enums.GiftStatus;
import be.wishlistAPI.javabeans.Gift;
import be.wishlistAPI.javabeans.GiftList;
import oracle.jdbc.OracleTypes;

public class GiftDAO extends DAO<Gift> {
	
	public GiftDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Gift obj) {
		boolean success = false;
		String query = "{call INSERT_GIFT(?, ?, ?, ?, ?, ?, ?, ?)}";
		byte[] imageBytes = Base64.getDecoder().decode(obj.getImage());
		InputStream imagestream = new ByteArrayInputStream(imageBytes);
		
		
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setString(1, obj.getName());
			cs.setString(2, obj.getDescription());
			cs.setDouble(3, obj.getPrice());
			cs.setInt(4, obj.getPriority());
			cs.setString(5, obj.getStatus().toString());
			cs.setBinaryStream(6, imagestream);
			cs.setString(7, obj.getBuylink());
			cs.setInt(8, obj.getGiftlist().getIdgiftlist());

			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} 
		return success;
	}
	
	@Override
	public Gift find(int id) {
		Gift gift = null;
	       
		String query = "{? = call SELECT_GIFT(?)}";
		 
		try (CallableStatement cs = this.connect.prepareCall(query)) { 
			cs.registerOutParameter(1, OracleTypes.STRUCT, "TYP_GIFT");
			cs.setInt(2, id); 
			cs.executeQuery();
	            
			Object data = (Object) cs.getObject(1); 
			if (data != null) {
				Struct row = (Struct)data; 
				Object[] values = (Object[]) row.getAttributes();
				String name = String.valueOf(values[1]); 
				String description = String.valueOf(values[2]);	           
				double price = Double.parseDouble(String.valueOf(values[3]));
				int priority = Integer.parseInt(String.valueOf(values[4]));
				GiftStatus status = GiftStatus.valueOf(String.valueOf(values[5]));
				String image = null;
				Blob imageBlob = (Blob) values[6];
				if (imageBlob != null) {
					byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
					image = Base64.getEncoder().encodeToString(imageBytes);
				}
				
				String buylink = String.valueOf(values[7]);
				
				GiftList giftlist = new GiftList();
				giftlist.setIdgiftlist(Integer.parseInt(String.valueOf(values[8])));
				
		            
				gift = new Gift(id, name, description, price, priority, status, image, buylink, giftlist);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return gift;
	}
	
	@Override
	public ArrayList<Gift> findAll(int idgiftlist) {
		
		ArrayList<Gift> gifts = new ArrayList<Gift>();

		String query = "{? = call SELECT_GIFTLISTS_FROM_USER(?)}";

		try (CallableStatement cs = this.connect.prepareCall(query)) {
			
			cs.registerOutParameter(1, OracleTypes.ARRAY, "TYP_TAB_GIFT");
			cs.setInt(2, idgiftlist);
			cs.executeQuery();
			
			Array arr = cs.getArray(1); 
			if (arr != null) {
				Object[] data = (Object[]) arr.getArray(); 
				for (Object a : data) {
				    Struct row = (Struct) a;
				    Object[] values = (Object[]) row.getAttributes();
				    int id = Integer.parseInt(String.valueOf(values[0]));
				    String name = String.valueOf(values[1]); 
					String description = String.valueOf(values[2]);	           
					double price = Double.parseDouble(String.valueOf(values[3]));
					int priority = Integer.parseInt(String.valueOf(values[4]));
					GiftStatus status = GiftStatus.valueOf(String.valueOf(values[5]));
					String image = null;
					Blob imageBlob = (Blob) values[6];
					if (imageBlob != null) {
						byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
						image = Base64.getEncoder().encodeToString(imageBytes);
					}
					
					String buylink = String.valueOf(values[7]);
					
					GiftList giftlist = new GiftList();
					giftlist.setIdgiftlist(Integer.parseInt(String.valueOf(values[8])));
					
			            
					Gift gift = new Gift(id, name, description, price, priority, status, image, buylink, giftlist);
					gifts.add(gift);
				}
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} 
		return gifts;
	}

	

	@Override
	public boolean update(Gift obj) {
		boolean success = false;
		
		String query = "{call UPDATE_GIFT(?, ?, ?, ?, ?, ?, ?, ?)}";
		byte[] imageBytes = Base64.getDecoder().decode(obj.getImage());
		InputStream imagestream = new ByteArrayInputStream(imageBytes);
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setInt(1, obj.getIdGift());
			cs.setString(2,  obj.getName());
			cs.setString(3,  obj.getDescription());
			cs.setDouble(4, obj.getPrice());
			cs.setInt(5, obj.getPriority());
			cs.setString(6,  obj.getStatus().toString());
			cs.setString(7,  obj.getBuylink());
			cs.setBinaryStream(8, imagestream);
			
			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return success;
	}
	
	@Override
	public boolean delete(Gift obj) {
		boolean success = false;
		
		String query = "{call DELETE_GIFT(?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setInt(1, obj.getIdGift());
			
			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return success;
	}

	@Override
	public ArrayList<Gift> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
