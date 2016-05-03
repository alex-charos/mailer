package gr.charos.mailer.service;

import java.io.File;
import java.util.Set;

public interface AddressExtractor {
	
	Set<String> extractAddresses(File f);

}
