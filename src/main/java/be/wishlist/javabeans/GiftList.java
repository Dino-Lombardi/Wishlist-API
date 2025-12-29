package be.wishlist.javabeans;

public class GiftList
{
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public GiftList(int id) 
	{
		setId(id);
	}
	
}
