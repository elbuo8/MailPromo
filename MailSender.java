package test1;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MailSender extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 42198199007655394L;
	public MailSender() {

	}

	public boolean sendMessage(String[] contacts) {
		String subject = JOptionPane.showInputDialog("Insert subject");
		String message = JOptionPane.showInputDialog("Insert HTML/text");

		int selector = JOptionPane.showConfirmDialog(null, "Attachment", "Selector", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(selector == JOptionPane.YES_OPTION) {
			final JFileChooser chooser = new JFileChooser();
			final int retunValue = chooser.showOpenDialog(MailSender.this);
			chooser.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if(retunValue == JFileChooser.APPROVE_OPTION)
						attachment = chooser.getSelectedFile();
				}
			});
		}
		else
			attachment = null;
		Properties mail = new Properties();
		mail.setProperty("mail.transport.protocol", "smtp");
		mail.setProperty("mail.smtp.host", JOptionPane.showInputDialog("Insert SMTP host"));
		mail.setProperty("mail.smtp.auth", "true");
		//Hotmail fix from Hector Franqui
		mail.setProperty("mail.smtp.starttls.enable", "true");

		Session emailSession = Session.getDefaultInstance(mail, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(JOptionPane.showInputDialog("username"), JOptionPane.showInputDialog("password"));
			}
		});

		MimeMessage sender = new MimeMessage(emailSession);

		try {
			Transport transport = emailSession.getTransport("smtp");
			sender.setSubject(subject);
			transport.connect();

			Address[] recipients = new Address[contacts.length];
			for (int j = 0; j < recipients.length; j++) 
				recipients[j] = new InternetAddress(contacts[j]);

		    MimeBodyPart messageBodyPart = new MimeBodyPart();
		    messageBodyPart.setText(message);

		    Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(messageBodyPart);

		    messageBodyPart = new MimeBodyPart();
		    DataSource source = new FileDataSource(attachment);
		    messageBodyPart.setDataHandler(new DataHandler(source));
		    messageBodyPart.setFileName(attachment.getName());
		    multipart.addBodyPart(messageBodyPart);

		    sender.setContent(multipart);

			transport.sendMessage(sender,recipients);
			transport.close();
		} catch (NoSuchProviderException e) {
			return false;
		} catch (MessagingException e) {
			return false;
		}

		return true;
	}
	private File attachment;
}