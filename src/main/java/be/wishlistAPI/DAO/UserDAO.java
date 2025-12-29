package be.wishlistAPI.DAO;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;

import be.wishlistAPI.javabeans.User;
import oracle.jdbc.OracleTypes;

public class UserDAO extends DAO<User>{

	public UserDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(User obj) {
		boolean success = false;
		
		String query = "{call INSERT_USER(?, ?, ?, ?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setString(1, obj.getFirstname());
			cs.setString(2, obj.getLastname());
			cs.setString(3, obj.getUsername());
			cs.setString(4, obj.getPassword());
			
			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		return success;
	}
	
	@Override
	public User find(int id) {
		User user = null;
	       
		String query = "{? = call SELECT_USER(?)}";
		 
		try (CallableStatement cs = this.connect.prepareCall(query)) { 
			cs.registerOutParameter(1, OracleTypes.STRUCT, "TYP_USER");
			cs.setInt(2, id); 
			cs.executeQuery();
	            
			Object data = (Object) cs.getObject(1); 
			if (data != null) {
				Struct row = (Struct)data; 
				Object[] values = (Object[]) row.getAttributes();
				String firstname = String.valueOf(values[1]); 
				String lastname = String.valueOf(values[2]);	           
				String username = String.valueOf(values[3]);
				String password = String.valueOf(values[4]);
		            
				user = new User(id, firstname, lastname, username, password);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	@Override
	public ArrayList<User> findAll() {
		
		ArrayList<User> users = new ArrayList<User>();

		String query = "{? = call SELECT_USERS}";

		try (CallableStatement cs = this.connect.prepareCall(query)) {
			
			cs.registerOutParameter(1, OracleTypes.ARRAY, "TYP_TAB_USER");
			cs.executeQuery();
			
			Array arr = cs.getArray(1); 
			if (arr != null) {
				Object[] data = (Object[]) arr.getArray(); 
				for (Object a : data) {
				    Struct row = (Struct) a;
				    Object[] values = (Object[]) row.getAttributes();
				    String id = String.valueOf(values[0]);
				    int iduser = Integer.parseInt(id);
				    String firstname = String.valueOf(values[1]);
				    String lastname = String.valueOf(values[2]);
					String username = String.valueOf(values[3]);
					String password = String.valueOf(values[4]);				    
				    User user = new User(iduser, firstname, lastname, username, password);
				    users.add(user); 
				}
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} 
		return users;
	}

	public User find(String input_username, String input_password) {
		User user = null;
	       
		String query = "{? = call LOGIN(?, ?)}";
		 
		try (CallableStatement cs = this.connect.prepareCall(query)) { 
			cs.registerOutParameter(1, OracleTypes.STRUCT, "TYP_USER");
			cs.setString(2, input_username); 
			cs.setString(3, input_password);
			cs.executeQuery();
	            
			Object data = (Object) cs.getObject(1);
			if (data != null) {
				Struct row = (Struct)data; 
				Object[] values = (Object[]) row.getAttributes();
				int iduser = Integer.parseInt(String.valueOf(values[0]));
				String firstname = String.valueOf(values[1]); 
				String lastname = String.valueOf(values[2]);	           
				String username = String.valueOf(values[3]);
				String password = String.valueOf(values[4]);
		            
				user = new User(iduser, firstname, lastname, username, password);
			} 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}

	@Override
	public boolean update(User obj) {
		boolean success = false;
		
		String query = "{call UPDATE_USER(?, ?, ?, ?, ?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setInt(1, obj.getIdUser());
			cs.setString(2,  obj.getFirstname());
			cs.setString(3,  obj.getLastname());
			cs.setString(4,  obj.getUsername());
			cs.setString(5,  obj.getPassword());
			
			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return success;
	}
	
	@Override
	public boolean delete(User obj) {
		boolean success = false;
		
		String query = "{call DELETE_USER(?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setInt(1, obj.getIdUser());
			
			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return success;
	}

	@Override
	public ArrayList<User> findAll(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
