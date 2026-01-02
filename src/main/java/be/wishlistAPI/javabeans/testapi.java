package be.wishlistAPI.javabeans;

import java.time.LocalDateTime;
import java.util.ArrayList;

import be.wishlistAPI.enums.InvitationStatus;

public class testapi {
	public static void main(String[] args) {
		User user = User.getUser(26);
		GiftList gf = new GiftList(1,null,null,null,null,null,null,null);
		Invitation i = new Invitation(InvitationStatus.PENDING, user, gf,LocalDateTime.now());
		System.out.println(Invitation.create(i));
	}
}
