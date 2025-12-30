package be.wishlistAPI.javabeans;

import java.util.ArrayList;

import be.wishlistAPI.DAO.AbstractDAOFactory;
import be.wishlistAPI.DAO.DAO;
import be.wishlistAPI.DAO.GiftDAO;
import be.wishlistAPI.enums.GiftStatus;

public class Gift {
	private static final AbstractDAOFactory daoFactory = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static final DAO<Gift> GiftDAO = daoFactory.getGiftDAO();	
	private int idgift;
	private String name;
	private String description;
	private double price;
	private int priority;
	private GiftStatus status;
	private String image;
	private String buylink;
	private GiftList giftlist;
	
	public Gift() {}
	
	
	public Gift(int idgift, String name, String description, double price, int priority, GiftStatus status, String image,
			String buylink, GiftList giftlist) {
		this.idgift = idgift;
		this.name = name;
		this.description = description;
		this.price = price;
		this.priority = priority;
		this.status = status;
		this.image = image;
		this.buylink = buylink;
		this.giftlist = giftlist;
	}
	
	
	public int getIdGift() {
		return idgift;
	}

	public void setIdGift(int id) {
		this.idgift = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	public int getPriority() {
		return priority;
	}
	
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public GiftStatus getStatus() {
		return status;
	}
	
	
	public void setStatus(GiftStatus status) {
		this.status = status;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getBuylink() {
		return buylink;
	}
	
	public void setBuylink(String buylink) {
		this.buylink = buylink;
	}
	
	public GiftList getGiftlist() {
		return giftlist;
	}
	
	public void setGiftlist(GiftList giftlist) {
		this.giftlist = giftlist;
	}
	
	
	// Appel à la DAO
	
	public boolean insert() {
		return GiftDAO.create(this);
	}
	
	public boolean update() {
		return GiftDAO.update(this);
	}
	
	public boolean delete() {
		return GiftDAO.delete(this);
	}
	
	public static Gift getGift(int id) {
		return GiftDAO.find(id);
	}
	
	public static ArrayList<Gift> getGiftsByGiftList(int idgiftlist) {
		return GiftDAO.findAll(idgiftlist);
	}
	
	@Override
	public String toString() {
		return "Gift [idgift=" + idgift + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", priority=" + priority + ", status=" + status + ", image=" + image + ", buylink=" + buylink
				+ ", giftlist=" + giftlist + "]";
	}
}
