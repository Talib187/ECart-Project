package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public  FileInputStream fis;
	public FileOutputStream fos;
	public XSSFWorkbook wb;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;

	String path;

	public ExcelUtility(String path) {

		this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {

		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum()+1; // It return index of last row how I am adding 1 here.
		wb.close();
		fis.close();

		return rowCount;
	}

	public int getCellCount(String sheetName, int rowNum) throws IOException {

		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		int cellCount = row.getLastCellNum();
		wb.close();
		fis.close();

		return cellCount;
	}

	public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(colNum);

		DataFormatter formatter = new DataFormatter();
		String data;
		try {
			data = formatter.formatCellValue(cell); // return the formatted value of String
		}

		catch (Exception e) {

			data = "";
		}
		wb.close();
		fis.close();
		return data;
	}

}
