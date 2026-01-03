package be.wishlistAPI.javabeans;

import java.util.ArrayList;

import be.wishlistAPI.DAO.AbstractDAOFactory;
import be.wishlistAPI.DAO.DAO;
import be.wishlistAPI.DAO.UserDAO;

public class User {
	
	private static final AbstractDAOFactory daoFactory = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static final DAO<User> userDAO = daoFactory.getUserDAO();
	
	private int iduser;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private ArrayList<GiftList> giftlists;
	private ArrayList<Invitation> invitations;
	
	public User() {
		giftlists = new ArrayList<GiftList>();
		invitations = new ArrayList<Invitation>();
	}
	
	public User(int iduser, String firstname, String lastname, String username, String password) {
		this.iduser = iduser;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		
		giftlists = new ArrayList<GiftList>();
		invitations = new ArrayList<Invitation>();
	}

	public int getIdUser() {
		return iduser;
	}

	public void setIdUser(int iduser) {
		this.iduser = iduser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public ArrayList<GiftList> getGiftlists() {
		return giftlists;
	}

	public void setGiftlists(ArrayList<GiftList> giftlists) {
		this.giftlists = giftlists;
	}
	
	public void addGiftlist(GiftList giftlist) {
		this.giftlists.add(giftlist);
	}
	
	public void removeGiftlist(GiftList giftlist) {
		this.giftlists.remove(giftlist);
	}

	public ArrayList<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(ArrayList<Invitation> invitations) {
		this.invitations = invitations;
	}
	
	public void addInvitation(Invitation invitation) {
		this.invitations.add(invitation);
	}
	
	public void removeInvitation(Invitation invitation) {
		this.invitations.remove(invitation);
	}
	
	// Appel à la DAO
	
	public boolean insert() {
		return userDAO.create(this);
	}
	
	public boolean update() {
		return userDAO.update(this);
	}
	
	public boolean delete() {
		return userDAO.delete(this);
	}
	
	public static User getUser(int id) {
		return userDAO.find(id);
	}
	
	public static User getUser(String username) {
		return ((UserDAO) userDAO).find(username);
	}
	
	public static ArrayList<User> getUsers(){
		return userDAO.findAll();
	}
	
	public static User login (String username, String password) {
		return ((UserDAO) userDAO).find(username, password);
	}
}
