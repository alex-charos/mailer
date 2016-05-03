package gr.charos.mailer;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import gr.charos.mailer.service.AddressExtractor;
import gr.charos.mailer.service.impl.ExcelAddressExtractor;
import gr.charos.mailer.service.impl.PDFAddressExtractor;
import gr.charos.mailer.service.impl.RegexAddressParser;
import gr.charos.mailer.service.impl.TextAddressExtractor;


public class App {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Set<String> mails = new HashSet<String>();
		String command = "";
		while (!command.equalsIgnoreCase("quit")) {
			System.out.print("Enter Path to read addresses from, type 'Quit' to exit:");
			command = scanner.next();
			System.out.println(command);
			if (!command.equalsIgnoreCase("quit")) {
				AddressExtractor ae = getExtractorByFilename(command);
				Set<String> mailsCommand = (ae.extractAddresses(new File(command)));
				System.out.print(mailsCommand.size() + " Addresses extracted. ");
				mails.addAll(mailsCommand);
				System.out.println("...Added");
			}
		}
		System.out.println(mails.size() + " Total Addresses: ");
		System.out.println(mails);
	}
	
	private static AddressExtractor getExtractorByFilename(String filename) {
		String ext = filename.split("\\.(?=[^\\.]+$)")[1];
		if (ext.equalsIgnoreCase("xls") ) {
			return new ExcelAddressExtractor(new RegexAddressParser());
		} else if (ext.equalsIgnoreCase("pdf")) {
			return new PDFAddressExtractor(new RegexAddressParser());
		}  else if (ext.equalsIgnoreCase("txt") || ext.equalsIgnoreCase("html")) {
			return new TextAddressExtractor(new RegexAddressParser());
		} else {
			throw new RuntimeException("Unsupported file type!");
		}
	}
}
