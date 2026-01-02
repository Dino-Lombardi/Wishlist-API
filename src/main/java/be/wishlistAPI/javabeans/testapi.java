package be.wishlistAPI.javabeans;

import java.time.LocalDateTime;
import java.util.ArrayList;

import be.wishlistAPI.javabeans.Gift;
import be.wishlistAPI.enums.GiftStatus;
import be.wishlistAPI.enums.InvitationStatus;

public class testapi {
	public static void main(String[] args) {
	 ArrayList<Gift> gf = Gift.getGiftsByGiftList(1);
	 for(Gift g : gf) 
		{
			System.out.println(g.toString());
		}
	}
}
