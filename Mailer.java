package test1;

public class Mailer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Contacts contacts = new Contacts();
		MailSender sender = new MailSender();
		
		sender.sendMessage(contacts.getContacts());
		contacts.exit();

	}

}
