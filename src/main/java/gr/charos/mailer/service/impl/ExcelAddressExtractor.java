package gr.charos.mailer.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import gr.charos.mailer.service.AddressExtractor;
import gr.charos.mailer.service.AddressParser;

public class ExcelAddressExtractor implements AddressExtractor {

	public ExcelAddressExtractor(AddressParser addressParser) {
		super();
		this.addressParser = addressParser;
	}

	private AddressParser addressParser;

	public Set<String> extractAddresses(File f) {

		Set<String> mails = new HashSet<String>();
		FileInputStream file;
		try {
			file = new FileInputStream(f);

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			int numOfSheets = workbook.getNumberOfSheets();

			for (int i = 0; i < numOfSheets; i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						try {
							String s = cell.getStringCellValue();
							mails.addAll(addressParser.parseAddresses(s));

						} catch (Exception ex) {

						}

					}

				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mails;
	}

}
