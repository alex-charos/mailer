package gr.charos.mailer.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gr.charos.mailer.service.AddressExtractor;
import gr.charos.mailer.service.AddressParser;

public class TextAddressExtractor implements AddressExtractor {
	private AddressParser addressParser;

	public TextAddressExtractor(AddressParser addressParser) {
		super();
		this.addressParser = addressParser;
	}

	public Set<String> extractAddresses(File f) {
		
		Set<String> mails = new HashSet<String>();
		List<String> lines;
		try {
			lines = Files.readAllLines(f.toPath());

			for (String s : lines) {
				mails.addAll(addressParser.parseAddresses(s));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mails;
	}

}
