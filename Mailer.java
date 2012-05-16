package test1;

public class Mailer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Contacts contacts = new Contacts();
		MailSender sender = new MailSender();
		int menu = 0;
		do {
			//insert code shit.
		} while (menu != 0);
		sender.sendMessage(contacts.getContacts());
		contacts.exit();

	}

}
