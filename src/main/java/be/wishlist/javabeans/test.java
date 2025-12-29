package be.wishlist.javabeans;

import java.time.LocalDate;
import java.util.ArrayList;

import be.wishlist.enums.InvitationStatus;

public class test {

	public static void main(String[] args) {
		ArrayList<Reservation> res = new ArrayList<Reservation>();
		User user = User.getUser(1);
		Gift gf = new Gift(1);
		res = Reservation.findGiftReservations(gf);
		
		for(Reservation r : res) 
		{
			System.out.println(r.toString());
		}
		
	}

}
