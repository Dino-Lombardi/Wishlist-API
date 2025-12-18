package be.wishlist.DAO;

import java.sql.Connection;
import java.util.ArrayList;

import be.wishlist.javabeans.Invitation;

public class InvitationDAO extends DAO<Invitation> {
	
	public InvitationDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Invitation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Invitation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Invitation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Invitation find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Invitation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
