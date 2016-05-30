package gr.charos.mailer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import gr.charos.mailer.model.CommandResult;
import gr.charos.mailer.model.Email;
import gr.charos.mailer.service.AddressExtractor;
import gr.charos.mailer.service.AddressReader;
import gr.charos.mailer.service.ApplicationManager;
import gr.charos.mailer.service.MailSender;
import gr.charos.mailer.service.impl.AddressReaderImpl;
import gr.charos.mailer.service.impl.CommandManagerImpl;
import gr.charos.mailer.service.impl.ExcelAddressExtractor;
import gr.charos.mailer.service.impl.MailSenderImpl;
import gr.charos.mailer.service.impl.PDFAddressExtractor;
import gr.charos.mailer.service.impl.RegexAddressParser;
import gr.charos.mailer.service.impl.TextAddressExtractor;

public class App {
	public static void main(String[] args) {

		
		String propFile = System.getProperty("propFile") == null ? "config.properties" : System.getProperty("propFile");
		System.out.println("Loading props from :" + propFile);
		Properties props = getProperties(propFile);
		if (props == null) {
			System.out.println("No props file loaded. Please load file by setting the 'propFile' argument variable.");
			System.exit(-1);
		}
		

		ApplicationManager am = configureByProperties(props);
		Scanner scanner = new Scanner(System.in);
		CommandResult cr = null;
		String command = null;
		am.status();
		am.help();
		
		while (cr != CommandResult.exit) {
			System.out.println("Enter Command: ");
			command = scanner.next();
			cr = am.manageCommand(command);
			if(cr.equals(CommandResult.failure)) {
				System.out.println("There was an error!");
			}
		}
 
		scanner.close();
	}
	private static ApplicationManager configureByProperties(Properties props){
		MailSender ms = new MailSenderImpl(props.getProperty("mail.server"), props.getProperty("mail.port", ""));
		AddressReader ar = new AddressReaderImpl();
		ApplicationManager cm = new CommandManagerImpl(ms, ar);
		
		Set<String> paths = new HashSet<String>();
		String[] preloaded = props.get("preloaded").toString().split(",");
		for (String preload : preloaded) {
			paths.add(preload);
		}
		
		for (String path : paths) {
			cm.addMails(ar.readAddresses(path));
		}
		String sender = props.getProperty("mail.defaultSender");
		String subject = props.getProperty("mail.defaultSubject");
		String body = props.getProperty("mail.defaultBody");

		if (sender != null) {
			cm.setSenderEmail(sender);
		}
		if (subject != null) {
			cm.setSubject(subject);
		}
		if (body != null) {
			cm.setBody(body);
		}
		return cm;
		
	}
	private static Properties getProperties(String propFileName) {
		Properties prop = null;
		try {
			
			InputStream inputStream = new FileInputStream(propFileName);
			prop = new Properties();
			prop.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Property file not found! Exiting...");
			System.exit(-1);
		}

		return prop;
	}

	private static void sendMails(MailSender sender, Set<String> mails, Email mail) {
		System.out.println(mails.size() + " Total Addresses: ");
		int counter = 1;

		for (String email : mails) {
			System.out.println("Sending " + counter++ + " of " + mails.size() + " (" + email + ")");
			mail.setRecipientEmail(email);

			boolean sent = sender.sendMail(mail);
			if (sent) {
				System.out.println("Sent!");
			} else {
				System.out.println("Not Sent... Waiting for 5 seconds and trying again");
				try {
					Thread.sleep(5 * 1000);
					sent = sender.sendMail(mail);
					System.out.println("Not Sent... Giving up at :" + email);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static Set<String> readByFilename(String filename) {
		Set<String> mails = new HashSet<String>();
		AddressExtractor ae = getExtractorByFilename(filename);
		Set<String> mailsCommand = (ae.extractAddresses(new File(filename)));
		System.out.print(mailsCommand.size() + " Addresses extracted. ");
		mails.addAll(mailsCommand);
		System.out.println("...Added");
		return mails;
	}

	private static AddressExtractor getExtractorByFilename(String filename) {
		String ext = filename.split("\\.(?=[^\\.]+$)")[1];
		if (ext.equalsIgnoreCase("xls")) {
			return new ExcelAddressExtractor(new RegexAddressParser());
		} else if (ext.equalsIgnoreCase("pdf")) {
			return new PDFAddressExtractor(new RegexAddressParser());
		} else if (ext.equalsIgnoreCase("txt") || ext.equalsIgnoreCase("html")) {
			return new TextAddressExtractor(new RegexAddressParser());
		} else {
			throw new RuntimeException("Unsupported file type!");
		}
	}
}
