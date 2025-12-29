package be.wishlist.javabeans;

public class Gift 
{
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Gift(int id) 
	{
		setId(id);
	}
	
}
