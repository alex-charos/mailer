package gr.charos.mailer.service;

import gr.charos.mailer.model.Email;

public interface MailSender {
	
	boolean sendMail(Email email);

}
