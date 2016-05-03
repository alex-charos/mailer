package gr.charos.mailer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Set;

import org.junit.Test;

import gr.charos.mailer.service.AddressExtractor;
import gr.charos.mailer.service.impl.ExcelAddressExtractor;
import gr.charos.mailer.service.impl.PDFAddressExtractor;
import gr.charos.mailer.service.impl.RegexAddressParser;
import gr.charos.mailer.service.impl.TextAddressExtractor;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	
	@Test
	public void testReadFileTxt() {
		AddressExtractor ae = new TextAddressExtractor( new RegexAddressParser());
		
		Set<String> mails =  ae.extractAddresses(new File("src/test/resources/schools-a.txt"));
		assertEquals(4,mails.size());
		
	}
	
	@Test
	public void testReadFilePDF() {
		AddressExtractor ae = new PDFAddressExtractor( new RegexAddressParser());
		
		Set<String> mails =ae.extractAddresses(new File("src/test/resources/ASKG10.pdf"));
		assertEquals(4,mails.size());
		
		
	}
	
	@Test
	public void testReadFileExcel() {
		AddressExtractor ae = new ExcelAddressExtractor( new RegexAddressParser());
		
		Set<String> mails =ae.extractAddresses(new File("src/test/resources/spreadsheet.xls"));
		assertEquals(4,mails.size());
		
		
	}
	
}
