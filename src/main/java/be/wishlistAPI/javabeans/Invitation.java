package be.wishlistAPI.javabeans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import be.wishlistAPI.DAO.AbstractDAOFactory;
import be.wishlistAPI.DAO.DAO;
import be.wishlistAPI.DAO.InvitationDAO;
import be.wishlistAPI.DAO.UserDAO;
import be.wishlistAPI.enums.InvitationStatus;

public class Invitation 
{
	//Attributs
	
	private static final AbstractDAOFactory daoFactory = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static final DAO<Invitation> invitationDAO = daoFactory.getInvitationDAO();
	
	private int id;
	private LocalDateTime sentdate;
	private InvitationStatus status;
	private User user;
	private GiftList giftlist;
	
	//Getters Setters
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDateTime getSentdate() {
		return sentdate;
	}
	
	public void setSentdate(LocalDateTime sentdate) {
		this.sentdate = sentdate;
	}
	
	public InvitationStatus getStatus() {
		return status;
	}
	
	public void setStatus(InvitationStatus status) {
		this.status = status;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public GiftList getGiftlist() {
		return giftlist;
	}
	
	public void setGiftlist(GiftList giftlist) {
		this.giftlist = giftlist;
	}
	
	
	//Constructeurs
	
	public Invitation(InvitationStatus status, User user, GiftList list) 
	{
		setSentdate(LocalDateTime.now());
		setStatus(status);
		setUser(user);
		setGiftlist(list);
		user.addInvitation(this);
		//list.addinvitation
	}
	
	public Invitation( InvitationStatus status, User user, GiftList list ,LocalDateTime date) 
	{
		this(status,user,list);
		setSentdate(date);
	}
	
	public Invitation(int id, InvitationStatus status, User user, GiftList list ,LocalDateTime date) 
	{
		this(status,user,list);
		setSentdate(date);
		setId(id);
	}
	
	
	
	//Méthodes
	
	@Override public String toString() 
	{
		return "Invitation {" + "id=" + id + ", sentdate=" + sentdate + ", status=" + status + ", user=" + (user != null ? user.getIdUser() : "null") + ", giftlist=" + (giftlist != null ? giftlist.getIdgiftlist() : "null") + '}'; 
	}
	
	public boolean create(Invitation obj) 
	{
		return invitationDAO.create(obj);
	}
	
	public boolean update(Invitation obj) 
	{
		return invitationDAO.update(obj);
	}
	
	public boolean delete(Invitation obj) 
	{
		return invitationDAO.delete(obj);
	}
	
	public static Invitation find(int id) 
	{
		return invitationDAO.find(id);
	}

	public static ArrayList<Invitation> findUserInvitations(User user) 
	{
		return ((InvitationDAO) invitationDAO).findUserInvitations(user);
		
	}
	
	public static ArrayList<Invitation> findGiftListInvitations(GiftList gf) 
	{
		return ((InvitationDAO) invitationDAO).findGiftListInvitation(gf);
	}
	
	public int getInvitationId(Invitation i) 
	{
		return ((InvitationDAO) invitationDAO).getInvitationId(i);
	}
}
