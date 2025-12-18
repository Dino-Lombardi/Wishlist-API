package be.wishlist.DAO;

import java.sql.Connection;
import java.util.ArrayList;

import be.wishlist.javabeans.GiftList;

public class GiftListDAO extends DAO<GiftList> {
	
	public GiftListDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(GiftList obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(GiftList obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(GiftList obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GiftList find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GiftList> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GiftList> findAll(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
