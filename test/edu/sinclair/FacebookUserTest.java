package edu.sinclair;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class FacebookUserTest {

	
	List<FacebookUser> test = new ArrayList<>();
	
	@Test
	public void testAddGetRetrieveFriends() {
		FacebookUser han = new FacebookUser("Han", "test");
		FacebookUser luke = new FacebookUser("Luke", "test");
		FacebookUser leia = new FacebookUser("Leia", "test");
		
		try {
			han.addFriend(luke);
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Added this, idk if this is right
			Assert.fail(e.getMessage());
		}
		try {
			han.addFriend(leia);
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Added this, idk if this is right
			Assert.fail(e.getMessage());
		}
		
		
		test.add(luke);
		test.add(leia);
		
		Assert.assertEquals(han.getFriends(), test);
	}
	
	@Test
	public void testHelp() {
		FacebookUser han = new FacebookUser("Han", "test");
		han.setPasswordHint("enter a valid password");
		Assert.assertEquals("enter a valid password", han.getPasswordHelp());
	}
	

	@Test
	public void testErrorCheckingAddingFriend() {
	  FacebookUser han = new FacebookUser("han", "test");
	  FacebookUser luke = new FacebookUser("luke", "test");

	  try {
	    han.addFriend(luke);
	    han.addFriend(luke);

	    Assert.fail("Should have failed");
	  } catch (FacebookException exception) {
	    String msg = exception.getMessage();
	    Assert.assertEquals("You are already friends with this user", msg);
	  }
	}
	

	@Test
	public void testErrorCheckingRemovingFriend() {
	  FacebookUser han = new FacebookUser("han", "test");
	  FacebookUser luke = new FacebookUser("luke", "test");
	  FacebookUser leia = new FacebookUser("leia", "test");
	  
	  try {
	    han.addFriend(luke);
	    han.removeFriend(leia);

	    Assert.fail("Should have failed");
	  } catch (FacebookException exception) {
	    String msg = exception.getMessage();
	    Assert.assertEquals("You are NOT friends with this user", msg);
	  }
	}
	

	@Test
	public void testSorting() throws FacebookException {
		FacebookUser han = new FacebookUser("Han", "test");
		FacebookUser luke = new FacebookUser("LUKE", "test");
		FacebookUser leia = new FacebookUser("leia", "test");
		
		han.addFriend(luke);
		han.addFriend(leia);
		
		List<FacebookUser> friends = han.getFriends();
		java.util.Collections.sort(friends);
		Assert.assertEquals(leia, friends.get(0));
		Assert.assertEquals(luke,friends.get(1));
		
	}
	
	@Test
	public void testRemoveFriend() throws FacebookException {
		FacebookUser han = new FacebookUser("Han", "test");
		FacebookUser luke = new FacebookUser("LUKE", "test");
		//Add luke as a friend
		han.addFriend(luke);
		//Remove luke from friends
		han.removeFriend(luke);
		List<FacebookUser> friends = han.getFriends();
		
		Assert.assertFalse(friends.contains(luke));
	}
}
