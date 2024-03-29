package test1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Contact management class
 * @author yamilasusta
 *
 */
public class Contacts {

	/**
	 * Default constructor 
	 * Decides whether to start a new file or read a file
	 */
	public Contacts() {
		if(!isEmpty())
			startContactList();
		else
			readFile();
		sort();

	}

	/**
	 * Sort the list of contacts in lexical order
	 */
	private void sort() {
		List<String> list = Arrays.asList(contact);
		Collections.sort(list);
		contact = (String[]) list.toArray();
	}
	
	/**
	 * Verify the existence of the contact file
	 * @return Status of the file
	 */
	private boolean isEmpty() {
		return new File("contacts.dat").exists();
	}
	
	/**
	 * Load contacts from file
	 */
	private void readFile() {
		ArrayList<String> parseList = new ArrayList<String>();
		try {
			Scanner reader = new Scanner(new FileReader("contacts.dat"));

			while (reader.hasNext())
				parseList.add(reader.nextLine());

			reader.close();
		} 
		catch(Exception e) {
			return;
		}

		contact = new String[parseList.size()];
		for (int i = 0; i < contact.length; i++) 
			contact[i] = parseList.get(i);
	}

	/**
	 * Start a new contact list
	 */
	private void startContactList() {

		String parse;
		do {
			parse = JOptionPane.showInputDialog("Insert contacts separated by commas");
		} while (parse.isEmpty());	

		parse = parse.toLowerCase();

		if (!parse.contains(",")) {
			contact = new String[1];
			contact[0] = parse;
		}
		else 
			contact = parse.split(",");
	}

	/**
	 * 
	 * @return All the contacts
	 */
	public String[] getContacts() {
		return contact;
	}

	/**
	 * Search for a contact in the list
	 * @param Email Contact to be searched
	 * @return Whether or not the contact is registered
	 */
	private int findContact(String Email) {
		for (int i = 0; i < contact.length; i++) 
			if (contact[i].equals(Email.trim().toLowerCase())) 
				return i;
		return -1;
	}
	
	/**
	 * Remove contact from list
	 * @param Email Contact to be removed
	 */
	public void removeContact(String Email) {
		if (findContact(Email) > -1) {
			String[] temp = new String[contact.length-1];
			for (int i = 0; i < findContact(Email); i++) 
				temp[i] = contact[i];
			for (int i = findContact(Email); i < temp.length; i++) 
				temp[i] = contact[i+1];
			contact = temp;
		}
	}

	/**
	 * Add contacts to the list
	 * @param newContacts List to be added
	 */
	public void addContacts(String[] newContacts) {
		String[] temp = new String[newContacts.length + contact.length];
		for (int i = 0; i < contact.length; i++) 
			temp[i] = contact[i];
		int j = 0;
		for (int i = contact.length; i < temp.length; i++) 
			temp[i] = newContacts[j++];
		contact = temp;
		sort();
	}
	
	/**
	 * Write the list to a file for next use
	 * @return If the saving was successful 
	 */
	public boolean exit() {
		try {
			FileWriter fw = new FileWriter("contacts.dat", false);
			
			for (int i = 0; i < contact.length; i++) 
				fw.write(contact[i] + "\n");
			
			fw.close();
		} 
		catch (IOException e) {
			return false;
		}
		return true;
	}

	private String[] contact;
}

