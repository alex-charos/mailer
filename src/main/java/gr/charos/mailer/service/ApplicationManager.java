package gr.charos.mailer.service;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import gr.charos.mailer.model.CommandResult;
import gr.charos.mailer.model.Email;

public abstract class ApplicationManager {
	Deque<String> previousCommands;
	protected MailSender mailSender;
	protected AddressReader addressReader;
	protected Email mail;
	protected Set<String> emails;

	
	public abstract CommandResult manageCommand(String command);

	public abstract void status(); 
	
	public void detail() {
		for (String mail : getEmails()) {
			System.out.println(mail);
		}
	}

	public void quit() {
		System.out.println("GoodBye!");
		System.exit(0);
	}

	public void prev() {
		System.out.println("Executing Previous command");
		manageCommand(getPreviousCommands().peek());
	}

	public abstract void log();
	
	public abstract void help();
	
	public Deque<String> getPreviousCommands() {
		if (previousCommands == null) {
			previousCommands = new LinkedList<String>();

		}
		return previousCommands;
	}

	public void setPreviousCommands(Deque<String> previousCommands) {
		this.previousCommands = previousCommands;
	}

	public Set<String> getEmails() {
		if (emails == null) {
			emails = new HashSet<String>();
		}
		return emails;
	}

	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}
	
	
	public void setSenderEmail(String email) {
		mail.setSenderEmail(email);
	}
	
	
	public void setSubject(String subject) {
		mail.setSubject(subject);
	}
	
	public void setBody(String body) {
		mail.setBody(body);
	}
	
	public void addMails(Set<String> mails) {
		getEmails().addAll(mails);
	}
	
	
	

}
