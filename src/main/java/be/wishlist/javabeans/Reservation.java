package be.wishlist.javabeans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import be.wishlist.DAO.AbstractDAOFactory;
import be.wishlist.DAO.DAO;
import be.wishlist.DAO.ReservationDAO;

public class Reservation
{
	private static final AbstractDAOFactory daoFactory = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static final DAO<Reservation> reservationDAO = daoFactory.getReservationDAO();
	
	private int id;
	private LocalDate reservationDate;
	private double amount;
	private boolean isgrouppurchase;
	private User user;
	private Gift gift;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getReservationDate() {
		return reservationDate;
	}
	
	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		if(amount<=0) 
		{
			System.out.println("Montant invalide");
		}
		else 
		{
			this.amount = amount;
		}
		
	}
	
	public boolean isIsgrouppurchase() {
		return isgrouppurchase;
	}
	
	public void setIsgrouppurchase(boolean isgrouppurchase) {
		this.isgrouppurchase = isgrouppurchase;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Gift getGift() {
		return gift;
	}
	
	public void setGift(Gift gift) {
		this.gift = gift;
	}
	
	//constructeurs
	
	public Reservation(LocalDate date, double amount, boolean isgroup, User user, Gift gift) 
	{
		setReservationDate(date);
		setAmount(amount);
		setIsgrouppurchase(isgroup);
		setUser(user);
		setGift(gift);
	}
	
	public Reservation(int id,LocalDate date, double amount, boolean isgroup, User user, Gift gift) 
	{
		this(date,amount,isgroup,user,gift);
		setId(id);
	}
	
	public Reservation(int id) 
	{
		setId(id);
	}
	
	//méthodes
	

	public static boolean create(Reservation r) 
	{
	   return reservationDAO.create(r);
	}

	public static Reservation find(int id) 
	{
	   return reservationDAO.find(id);
	}

	public static boolean update(Reservation r) 
	{
	   return reservationDAO.update(r);
	}

	public static boolean delete(Reservation r) 
	{
	   return reservationDAO.delete(r);
	}


	public static ArrayList<Reservation> findAll() 
	{
	   return reservationDAO.findAll();
	}

	public static ArrayList<Reservation> findUserReservations(User user) 
	{
	   return ((ReservationDAO) reservationDAO).findUserReservations(user);
	}


	public static ArrayList<Reservation> findGiftReservations(Gift gift) 
	{
	   return ((ReservationDAO) reservationDAO).findGiftReservations(gift);
	}

	@Override
	public String toString() {
	    return "Reservation {" +"id=" + id +", reservationDate=" + reservationDate +", amount=" + amount +", isGroupPurchase=" + isgrouppurchase +
	    		", user=" + (user != null ? user.getIdUser() + " - " + user.getFirstname() + " " + user.getLastname() : "null") +
	            ", gift=" + (gift != null ? gift.getId() + " - "  /*gift.getName()*/ : "null") +'}';
	}

	
}
