package edu.sinclair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Facebook {
	private List<FacebookUser> users;	
	private static final String PATH = System.getProperty("user.home") + File.separator + "sinclair" + File.separator;
	private static final String FILE_NAME = "FacebookUsers.dat";
	
	
	
	public static void main(String args[]) throws FileNotFoundException, ClassNotFoundException {
		boolean shouldContinue = true;
		Scanner sc = new Scanner(System.in);
		Facebook fb = new Facebook();
		String user;

		fb.load();
		
		do {
			File file = new File(PATH + FILE_NAME);
			
			
			System.out.println("Menu");
			System.out.println("1: List Users,");
			System.out.println("2: Add User,");
			System.out.println("3: Delete User,");
			System.out.println("4: Get Password Hint,");
			System.out.println("5: Quit");
			
			int menuSelection = sc.nextInt();
			
			if(menuSelection < 1 || menuSelection > 5) {
				System.out.println("Invalid selection");
			}else {
			switch(menuSelection) {
			
			case 1: 
				fb.listUsers();
			break;
			
			case 2: 
				sc.nextLine();
				System.out.println("User to add: ");
				user = sc.nextLine();
				fb.addUser(user);
				
			break;
				
			case 3:
				sc.nextLine();
				System.out.println("User to delete: ");
				user = sc.nextLine();
				fb.deleteUser(user);
				
			break;
			
			case 4: 
				sc.nextLine();
				System.out.println("Enter a username: ");
				user = sc.nextLine();
				fb.displayPasswordHint(user);
			break;
			
			case 5:
				if(file.exists()) {
					file.delete();
				}
				System.out.println("Goodbye!");
				shouldContinue = false;
				sc.close();
				fb.save();
			break;
			}
		}
			
				
		}
		while(shouldContinue);
				
	}
	
	public Facebook(){
		users = new ArrayList<>();
	}
	
	public void addUser(String user) {
		Scanner sc = new Scanner(System.in);

		List<String> stringOfUsers = new ArrayList<>(); //New arrayList for holding objects converted to strings
		//Conversion process
		for(int i = 0; i < users.size(); i++) {
			stringOfUsers.add(users.get(i).getUsername());
		}
		
		//Determine if this user already exists
		if(stringOfUsers.contains(user)) {
			System.out.println("User already exists!");
		}else {
			//If not, create a new user with a password they provide, and add it to the list of users
			System.out.println("Please enter a password for this user: ");
			String password = sc.nextLine();
			FacebookUser newUser = new FacebookUser(user, password);
			System.out.println("Enter a password hint: ");
			String hint = sc.nextLine();
			newUser.setPasswordHint(hint);
			users.add(newUser);
		}
		//sc.close();
	}
	
	public void listUsers() {
		for(int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getUsername());
		}
	}
	
	public void deleteUser(String user) {
		Scanner sc = new Scanner(System.in);

		
		FacebookUser foundUser = null;
		boolean isFound = false;
		List<String> stringOfUsers = new ArrayList<>(); //New arrayList for holding objects converted to strings
		//Conversion process
		for(int i = 0; i < users.size(); i++) {
			stringOfUsers.add(users.get(i).getUsername());
		}
		
		//Determine if this user already exists
		if(stringOfUsers.contains(user)) {
			//If it does exist, loop through users to find the correct username and delete it
			for(FacebookUser person : users ) {
				if(person.getUsername().equals(user)){
					System.out.println("Enter the password for this user: ");
					String password = sc.nextLine();
					if(password.equals(person.getPassword())){
						foundUser = person;
						isFound = true;
					}else {
						System.out.println("*Incorrect Password*");
					}
				}
			}
			if(isFound) {
				this.users.remove(foundUser);
			}
		}else {
			//If not, create a new user with a password they provide, and add it to the list of users
			System.out.println("This user does not yet exist!");
		}
		//sc.close();
	}
	
	public void displayPasswordHint(String user) {
		List<String> stringOfUsers = new ArrayList<>(); //New arrayList for holding objects converted to strings
		//Conversion process
		for(int i = 0; i < users.size(); i++) {
			stringOfUsers.add(users.get(i).getUsername());
		}
		
		//Determine if this user already exists
		if(stringOfUsers.contains(user)) {
			//If it does exist, loop through users to find the correct username and delete it
			for(FacebookUser person : users ) {
				if(person.getUsername().equals(user)){
					System.out.println(person.getPasswordHelp());
				}
			}
		}else {
			//Do nothing
		}
	}

	public void load() throws FileNotFoundException, ClassNotFoundException {
		File file = new File(PATH + FILE_NAME);
		File path = new File(PATH);

		Facebook fb = null;
		
		if(!path.exists()) {
			System.out.println("Path does not exists. Creating...");
			new File(PATH).mkdirs();
		}
		
		if(file.exists()) {	
			try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(PATH + FILE_NAME));){
				this.users = (List<FacebookUser>) input.readObject();				
			}catch (IOException e){
				e.printStackTrace();
			}
		}else {
			this.equals(fb);
		}
		file.delete();
	}
	
	public void save() {
		File file = new File(PATH);
		if(file.exists()) {
		
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(PATH + FILE_NAME));){
			output.writeObject(this.users);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		}else {
			System.out.println("Directory does not exist");
		}
	}
	
}
