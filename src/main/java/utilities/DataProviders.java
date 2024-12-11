package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	// We will create the all data provider method here if we need new data provider we will add new data provider method.
	
	// Data Provider 1
	@DataProvider(name="loginData")
	public String[][] getData() throws IOException {

		String path = "C:\\Users\\mtali\\eclipse-workspace\\opencart\\testData\\Login-Data.xlsx";

		ExcelUtility xlutils = new ExcelUtility(path);

		int totalRows = xlutils.getRowCount("sheet1");
		int totalCols = xlutils.getCellCount("sheet1", 1);

		String logindata[][] = new String[totalRows-1][totalCols]; // Created two dimensional array

		for (int i = 1; i <= totalRows-1; i++) //
		{
			for (int j = 0; j < totalCols; j++) {

				logindata[i-1][j] = xlutils.getCellData("sheet1", i, j);
			}
		}

		return logindata;

	}

	// Data Provider 2  pulling this from the github.
	
	// Data Provider 3 and so on.
}
