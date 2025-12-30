package be.wishlistAPI.javabeans;

public class testapi {
	public static void main(String[] args) {
		Invitation i = Invitation.find(12);
		System.out.println(i.toString());
	}
}
