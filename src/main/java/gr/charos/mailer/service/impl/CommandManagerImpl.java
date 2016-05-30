package gr.charos.mailer.service.impl;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import gr.charos.mailer.model.CommandResult;
import gr.charos.mailer.model.Email;
import gr.charos.mailer.service.AddressReader;
import gr.charos.mailer.service.ApplicationManager;
import gr.charos.mailer.service.MailSender;

public class CommandManagerImpl extends ApplicationManager {


	public CommandManagerImpl(MailSender mailSender, AddressReader addressReader) {
		this.mailSender = mailSender;
		this.addressReader = addressReader;
		mail = new Email();
	}

	public CommandResult manageCommand(String command) {
		CommandResult cr = CommandResult.success;
		if (command != "prev") {
			getPreviousCommands().push(command);
		}
		String[] input = command.split(" ");

		try {
			switch (input[0]) {
			case "quit":
				quit();
				break;
			case "help":
				help();
				break;
			case "prev":
				prev();
				break;
			case "load":
				for (int i = 1; i < input.length; i++) {
					addMails(addressReader.readAddresses(input[i]));
				}
				break;
			case "status":
				status();
				if (input.length > 1 && input[1] == "detail") {
					detail();
				}
				break;
			case "mail":
				switch (input[1]) {
				case "sender":
					setSenderEmail(input[2]);
					
					break;
				case "subject":
					setSubject(input[2]);
					break;
				case "body":
					setBody(input[2]);
					break;
				default:
					break;
				}
				break;
			case "send":
				send();
				break;
			case "log":
				log();
				break;

			default:
				help();
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			cr = CommandResult.failure;
		}
		return cr;
	}

	public void log() {
		System.out.println("Displaying Previous commands");
		for (String command : getPreviousCommands()) {
			System.out.println(command);
		}
	}
	
	public void send(){
		int size = getEmails().size();
		System.out.println(size + " Total Addresses: ");
		int counter = 1;

		
		for (String email : getEmails()) {
			System.out.println("Sending " + counter++ + " of " + size + " (" + email + ")");
			mail.setRecipientEmail(email);

			boolean sent = mailSender.sendMail(mail);
			if (sent) {
				System.out.println("Sent!");
			} else {
				System.out.println("Not Sent... Waiting for 5 seconds and trying again");
				try {
					Thread.sleep(5 * 1000);
					sent = mailSender.sendMail(mail);
					System.out.println("Not Sent... Giving up at :" + email);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public void status() {
		System.out.println(getEmails().size() + " email addresses");
		System.out.println("From : " + mail.getSenderEmail() );
		System.out.println("Subject : " + mail.getSubject());
		System.out.println("Body : " + mail.getBody());
	}
	
	public void help() {

		System.out.println("Type 'load [filename]' to load addresses from paths (space-separated)");
		System.out.println("Type 'preload' to load pre-fetched addresses");
		System.out.println("Type 'status' to get overview of loaded messages");
		System.out.println("Type 'status detail' to get overview of loaded messages");
		System.out.println("Type 'mail sender [sender]' to set mail subject");
		System.out.println("Type 'mail subject [subject]' to set mail subject");
		System.out.println("Type 'mail body [body]' to set mail subject (HTML supported)");
		System.out.println("Type 'send' to send mail");
		System.out.println("Type 'prev' to execute previous command");
		System.out.println("Type 'log' to view command history");
		System.out.println("Type 'quit' to exit, ");
		System.out.println("Type 'help' for this message :) ");
	}

}
