package be.wishlist.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import be.wishlist.javabeans.User;

public class UserDAO extends DAO<User>{

	public UserDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(User obj) {
		boolean success = false;
		
		String query = "{call insert_user(?, ?, ?, ?)}";
		try (CallableStatement cs = this.connect.prepareCall(query)){
			
			cs.setString(1, obj.getUsername());
			cs.setString(2, obj.getPassword());
			cs.setString(3, obj.getFirstname());
			cs.setString(4, obj.getLastname());
			
			cs.executeUpdate();
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		return success;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User find(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> findAll(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
