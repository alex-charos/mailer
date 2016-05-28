package gr.charos.mailer.service.impl;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import gr.charos.mailer.service.AddressExtractor;
import gr.charos.mailer.service.AddressReader;

public class AddressReaderImpl implements AddressReader {

	@Override
	public Set<String> readAddresses(String filename) {
		Set<String> mails = new HashSet<String>();
		AddressExtractor ae = getExtractorByFilename(filename);
		Set<String> mailsCommand = (ae.extractAddresses(new File(filename)));
		System.out.print(mailsCommand.size() + " Addresses extracted. ");
		mails.addAll(mailsCommand);
		System.out.println("...Added");
		return mails;
	}
	private AddressExtractor getExtractorByFilename(String filename) {
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
