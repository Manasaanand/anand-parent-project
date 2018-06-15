package net.orfdev.integration.test.util;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class PriceUtility {
	public static Xls_Reader datatable = null;
	public static WebDriver driver;

	public static WebDriver getDriver() {
		if (driver == null) {
			File pathToBinary = new File("C:\\Users\\Anand Jois\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
			// File pathToBinary = new File("C:\\Users\\Anand
			// Jois\\Documents\\Firefox53\\firefox.exe");
			// System.setProperty("webdriver.gecko.driver", "C:\\Users\\Anand
			// Jois\\Downloads\\FOLDERS\\geckodriver-v0.16.1-win64\\geckodriver.exe");
			FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			driver = new FirefoxDriver();
		}
		return driver;
	}

	public static Object[][] getData(String filePath, String testName) {
		// return test data;
		// read test data from xls
		if (datatable == null) {
			// load the suite 1 sheet
			datatable = new Xls_Reader(filePath);

		}

		int rows = datatable.getRowCount(testName) - 1;
		if (rows <= 0) {
			Object[][] testData = new Object[1][0];
			return testData;

		}
		rows = datatable.getRowCount(testName); // 3
		int cols = datatable.getColumnCount(testName);
		System.out.println("Test Name -- " + testName);
		System.out.println("total rows -- " + rows);
		System.out.println("total cols -- " + cols);
		Object data[][] = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {

			for (int colNum = 0; colNum < cols; colNum++) {
				try{
				data[rowNum - 2][colNum] = datatable.getCellData(testName, colNum, rowNum);
				
//				System.out.println(data[rowNum - 2][colNum]);
				}catch(NumberFormatException ex){ // handle your exception
					   
					}
			}
		}

		return data;

	}

}
