package gr.charos.mailer.service;

import java.util.Set;

public interface AddressParser {

	Set<String> parseAddresses(String s);
}
