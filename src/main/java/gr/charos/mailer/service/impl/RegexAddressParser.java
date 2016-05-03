package gr.charos.mailer.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gr.charos.mailer.service.AddressParser;

public class RegexAddressParser implements AddressParser {

	public Set<String> parseAddresses(String s) {
		Set<String> mails = new HashSet<String>();
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(s);
		while (m.find()) {
			mails.add(m.group());
		}
		return mails;
	}

}
