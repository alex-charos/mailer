package gr.charos.mailer.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import gr.charos.mailer.service.AddressExtractor;
import gr.charos.mailer.service.AddressParser;

public class PDFAddressExtractor implements AddressExtractor {
	private AddressParser addressParser;

	
	public PDFAddressExtractor(AddressParser addressParser) {
		super();
		this.addressParser = addressParser;
	}


	public Set<String> extractAddresses(File f) {
		Set<String> mails = new HashSet<String>();
		try {
			PDFTextStripper stripper = new PDFTextStripper();
			PDDocument pddoc = PDDocument.load(f);

			String s = stripper.getText(pddoc);
			mails.addAll(addressParser.parseAddresses(s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mails;
	}

}
