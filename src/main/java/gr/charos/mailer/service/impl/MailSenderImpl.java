package gr.charos.mailer.service.impl;


import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import gr.charos.mailer.model.Email;
import gr.charos.mailer.service.MailSender;

public class MailSenderImpl implements MailSender {
	
	private String smtpServer;
	private String port;
	
	public MailSenderImpl(String smtpServer, String port) {
		this.smtpServer = smtpServer;
		this.port=port;
		
	}

	public boolean sendMail(Email email) {
		Properties props = new Properties();
		props.put("mail.smtp.host", this.smtpServer);
		props.put("mail.smtp.port", this.port);

		Session session = Session.getInstance(props);

		try {

			Message message = new MimeMessage(session);
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(email.getSenderEmail(), null));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email.getRecipientEmail()));
			message.setSubject(email.getSubject());
			message.setContent(email.getBody(),"text/html; charset=utf-8");

			Transport.send(message);

			System.out.println("Done");

		}  catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
