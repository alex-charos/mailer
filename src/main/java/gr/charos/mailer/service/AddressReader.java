package gr.charos.mailer.service;

import java.util.Set;

public interface AddressReader {

	Set<String> readAddresses(String path);
}
