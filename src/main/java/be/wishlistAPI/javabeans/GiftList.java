package be.wishlistAPI.javabeans;

import java.time.LocalDate;
import java.util.ArrayList;


import be.wishlistAPI.DAO.AbstractDAOFactory;
import be.wishlistAPI.DAO.DAO;
import be.wishlistAPI.enums.GiftListStatus;


public class GiftList {
	
	private static final AbstractDAOFactory daoFactory = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static final DAO<GiftList> GiftListDAO = daoFactory.getGiftListDAO();
	
	
	private int idgiftlist;
	private String title;
	private String description;
	private LocalDate creationdate;
	private LocalDate expirationdate;
	private GiftListStatus status;
	private String sharelink;
	private User owner;
	private ArrayList<Gift> gifts;
	
	public GiftList() {
		this.gifts = new ArrayList<Gift>();
	}
	
	public GiftList(int idgiftlist, String title, String description, LocalDate creationdate,
			LocalDate expirationdate, GiftListStatus status, String sharelink, User owner) {
		this.idgiftlist = idgiftlist;
		this.title = title;
		this.description = description;
		this.creationdate = creationdate;
		this.expirationdate = expirationdate;
		this.status = status;
		this.sharelink = sharelink;
		this.owner = owner;
		this.gifts = new ArrayList<Gift>();
	}
	
	public int getIdgiftlist() {
		return idgiftlist;
	}
	
	public void setIdgiftlist(int idgiftlist) {
		this.idgiftlist = idgiftlist;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDate getCreationdate() {
		return creationdate;
	}
	
	public void setCreationdate(LocalDate creationdate) {
		this.creationdate = creationdate;
	}
	
	public LocalDate getExpirationdate() {
		return expirationdate;
	}
	
	public void setExpirationdate(LocalDate expirationdate) {
		this.expirationdate = expirationdate;
	}
	
	public GiftListStatus getStatus() {
		return status;
	}
	
	public void setStatus(GiftListStatus status) {
		this.status = status;
	}
	
	public String getSharelink() {
		return sharelink;
	}
	
	public void setSharelink(String sharelink) {
		this.sharelink = sharelink;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public ArrayList<Gift> getGifts() {
		return gifts;
	}
	
	public void setGifts(ArrayList<Gift> gifts) {
		this.gifts = gifts;
	}
	
	public void addGift(Gift gift) {
		this.gifts.add(gift);
	}
	
	public void removeGift(Gift gift) {
		this.gifts.remove(gift);
	}
	
	// Appeler dans GiftDAO
	
	public boolean insert() {
		return GiftListDAO.create(this);
	}
	
	public boolean update() {
		return GiftListDAO.update(this);
	}
	
	public boolean delete() {
		return GiftListDAO.delete(this);
	}
	
	public static GiftList getGiftList(int id) {
		return GiftListDAO.find(id);
	}
	
	public static ArrayList<GiftList> getGiftListsByUser(int iduser) {
		return GiftListDAO.findAll(iduser);
	}
	
	
	@Override
	public String toString() {
		return "GiftList [idgiftlist=" + idgiftlist + ", title=" + title + ", description=" + description
				+ ", creationdate=" + creationdate + ", expirationdate=" + expirationdate + ", status=" + status
				+ ", owner=" + owner + ", gifts=" + gifts + "]";
	}
		
	
	
	
}
