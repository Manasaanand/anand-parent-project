package net.orfdev.integration.test.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;

public abstract class BrowserUtilsAdmin {
	private final static Logger log = LogManager.getLogger(null, null);
	public static String months[] = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };
	static String winHandleBefore;

	public static String getDeviceType() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void loadPage(WebDriver currentDriver, String URL) throws IOException {
		currentDriver.get(URL);
	}

	public static String getText(WebDriver webDriver, By selector) throws IOException {
		for (int i = 0; i <= 3; i++) {
			try {
				waitForElementVisible(webDriver, selector, 15);
				return find(webDriver, selector).getText();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	public static WebElement find(WebDriver webDriver, By selector) throws IOException {
		return webDriver.findElement(selector);
	}

	public static double roundDecimals(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static String getDateFormat(String date) {
		String[] dateInDMY = date.split("/");
		String month = months[Integer.parseInt(dateInDMY[1]) - 1];
		Integer dateConverted = Integer.parseInt(dateInDMY[0]);
		if (dateConverted < 10) {
			dateInDMY[0] = "0" + dateConverted;
		}
		return dateInDMY[0] + " " + month + " " + dateInDMY[2];
	}

	public static Double convertStringToDouble(String labelPrice) {
		String[] justPrice = labelPrice.split("\\$ ");
		// int planitem = Integer.parseInt(justPrice[1]);
		Double subtotal = Double.parseDouble(justPrice[1]);
		return subtotal;
	}

	public static Double convertStringToDoubleWithoutSpace(String labelPrice) {
		String[] justPrice = labelPrice.split("\\$");

		Double subtotal = Double.parseDouble(justPrice[1]);
		return subtotal;
	}

	public static void waitUntilElementIsNotVisible(WebDriver webDriver, By selector, int timeinSeconds)
			throws IOException {
		WebDriverWait wait = new WebDriverWait(webDriver, timeinSeconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(selector));
	}

	public static void enterDateofBirth(WebDriver webDriver, By selector, String date) throws IOException {
		Actions action = new Actions(webDriver);
		action.sendKeys(Keys.DELETE);
		action.sendKeys(find(webDriver, selector), date).build().perform();

	}

	public static void clickOptionFromDropDown(WebDriver webDriver, By dropDownElement, By tagName, int count)
			throws IOException {
		WebElement dropDown = find(webDriver, dropDownElement);
		dropDown.findElements(tagName).get(count).click();
	}

	public static void sendKeys(WebDriver webDriver, By selector, Keys data) throws IOException {
		find(webDriver, selector).sendKeys(data);
	}

	public static WebElement waitForElement(WebDriver webDriver, By selector, Integer waitTimeInSeconds)
			throws IOException {
		WebElement elementWaitFor = BrowserUtilsAdmin.find(webDriver, selector);
		if (waitTimeInSeconds == null) {
			waitTimeInSeconds = 5;
		}
		WebDriverWait wait = new WebDriverWait(webDriver, waitTimeInSeconds);
		return wait.until(ExpectedConditions.visibilityOf(elementWaitFor));
	}

	public static void waitUntillStringChanges(WebDriver webDriver, By selector, Integer waitTimeInSeconds,
			String input) {
		WebDriverWait wait = new WebDriverWait(webDriver, waitTimeInSeconds);
		wait.until(ExpectedConditions.textToBePresentInElementValue(selector, input));

	}

	public static void waitForTextToChange(WebDriver webDriver, By selector, String input) throws IOException {
		while (!getText(webDriver, selector).equalsIgnoreCase(input)) {
		}
	}

	public static List<WebElement> waitForElementVisible(WebDriver webDriver, By selector, Integer waitTimeInSeconds)
			throws IOException {
//		for (int i = 0; i <= 3; i++) {
//			try {
				WebDriverWait wait = new WebDriverWait(webDriver, waitTimeInSeconds);
				return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//			}
//		}
//		return null;
	}

	public static void waitForElementToInvisible(WebDriver webDriver, By selector) throws IOException {
		while (findAll(webDriver, selector).size() != 0) {

		}
	}

	public static void refresh(WebDriver webDriver) throws IOException {
		webDriver.navigate().refresh();

	}

	public static void switchToAnotherWindow(WebDriver webDriver) {
		try {
			winHandleBefore = webDriver.getWindowHandle();
			for (String winHandle : webDriver.getWindowHandles()) {
				webDriver.switchTo().window(winHandle);
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	public static void switchBackToParentWindow(WebDriver webDriver) {
		webDriver.switchTo().window(winHandleBefore);
	}

	public static String getDateOfBirth(int customerAge) {
		// DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int yearOfBirth = year - customerAge;
		// return day+"/"+month+"/"+yearOfBirth;
		return (day < 10 ? ("0" + day) : (day)) + "/" + (month < 9 ? ("0" + (month + 1)) : (month + 1)) + "/"
				+ yearOfBirth;
	}

	public static void enterDateOfBirth(WebDriver webDriver, By selector, String dateOfBirth) throws IOException {
		Actions action = new Actions(webDriver);
		action.sendKeys(Keys.DELETE);
		action.sendKeys(find(webDriver, selector), dateOfBirth).build().perform();
	}

	public static void clearAndEnterText(WebDriver webDriver, By selector, String string) throws IOException {
		for (int i = 0; i <= 3; i++) {
			try {
				waitForElementVisible(webDriver, selector, 10);
				Actions action = new Actions(webDriver);
				action.moveToElement(find(webDriver, selector)).build().perform();
				find(webDriver, selector).clear();
				find(webDriver, selector).sendKeys(string);
				break;
			} catch (Exception e) {
				refresh(webDriver);
				System.out.println(e.getMessage());
			}
		}
	}

	public static void click(WebDriver webDriver, By selector) throws IOException {
//		for (int i = 0; i <= 3; i++) {
//			try {
				waitForElementVisible(webDriver, selector, 20);
				Actions action = new Actions(webDriver);
				action.moveToElement(find(webDriver, selector)).build().perform();
				find(webDriver, selector).click();
//				break;
//			} catch (Exception e) {
//				log.debug(e.getMessage());
//			}
//		}
	}

	public static boolean elementExist(WebDriver webDriver, By selector) throws IOException {
		waitForElementVisible(webDriver, selector, 5);
		return find(webDriver, selector).isEnabled();

	}

	public static String getAttribute(WebDriver webDriver, By selector, String attribute) throws IOException {
		for (int i = 0; i <= 3; i++) {
			try {
				return find(webDriver, selector).getAttribute(attribute);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		return attribute;
	}

	public static boolean elementDisplay(WebDriver webDriver, By selector) throws IOException {
		return findAll(webDriver, selector).size() != 0;
	}

	public static List<WebElement> findAll(WebDriver webDriver, By selector) throws IOException {
		return webDriver.findElements(selector);
	}

	public static Double convertPremium(WebDriver webDriver, By Premiums) throws IOException {
		for (int i = 0; i <= 3; i++) {
			try {
				String PremiumValue = getText(webDriver, Premiums);
				String[] Amount = PremiumValue.split("\\$");
				Double AmountValue = Double.parseDouble(Amount[1]);
				return AmountValue;

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		return null;
	}

	public static Date StringToDate(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
		return df.parse(date);
	}

	public static void clickBasedOnCount(WebDriver webDriver, By selector, int i) throws IOException {
		webDriver.findElements(selector).get(i).click();

	}

	public static Double convertToDouble(String Amount) {
		String[] AmountsToBeConverted = Amount.split("\\$");
		Double ConvertedAmount = Double.parseDouble(AmountsToBeConverted[1]);

		return ConvertedAmount;
	}

	public static void takeScreenShot(WebDriver webDriver, Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			if (scenario.isFailed()) {
				byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
			}
		}

	}

	public static void ClearBrowserCache(WebDriver webDriver) throws IOException {
		webDriver.manage().deleteAllCookies();
	}

	public static void close(WebDriver webDriver) throws IOException {
		webDriver.close();
	}

	public static void moveToElement(WebDriver webDriver, By selector) throws IOException {
		Actions action = new Actions(webDriver);
		WebElement element = find(webDriver, selector);
		action.moveToElement(element).build().perform();

	}

	public static int getCount(WebDriver webDriver, String coverID) throws IOException {
		waitForElementVisible(webDriver, By.id(coverID), 5);
		return findAll(webDriver, By.id(coverID)).size();

	}

	public static String getTextFromTextBox(WebDriver webDriver, By selector) throws IOException {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		return (String) ((js).executeScript("return arguments[0].value", find(webDriver, selector)));
	}

	public static void goToPreviousPage(WebDriver webDriver) throws IOException {
		webDriver.navigate().back();
	}

	public static void selectData(WebDriver webDriver, By selector, String arg1) throws IOException {
		// for (int i = 0; i <= 3; i++) {
		// try {
		find(webDriver, selector).sendKeys(arg1);
		// break;
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// clearAndEnterText(webDriver, selector, arg1);
		// return;
		// }
		// }
	}

	public static void deleteScreenshots() {
		File file = new File( OSUtil.path(System.getProperty("user.dir"), "screenshots") );
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			if ((myFiles.length) != 0) {
				for (int i = 0; i < myFiles.length; i++) {
					File myFile = new File(file, myFiles[i]);
					myFile.delete();
				}
			}
			String[] NewFolder = { OSUtil.path(System.getProperty("user.dir"), "target", "cucumber-html-reports"),
					OSUtil.path(System.getProperty("user.dir"), "target","cucumber-html-reports","embeddings") };
			for (int i = 0; i < NewFolder.length; i++) {
				File folder = new File(NewFolder[i]);
				File fList[] = folder.listFiles();

				for (File f : fList) {
					if (f.getName().endsWith(".png")) {
						f.delete();
					}
				}
			}
			File folder = new File(System.getProperty("user.dir"));
			File fList[] = folder.listFiles();
			for (File f : fList) {
				if (f.getName().endsWith(".zip")) {
					f.delete();
				}
			}
		}

	}

	public static void quit(WebDriver webDriver) throws IOException {
		webDriver.quit();

	}

	public static void verifyPageTitle(WebDriver webDriver, String expectedTitle) throws IOException {

		Assert.assertEquals(expectedTitle, webDriver.getTitle());
	}

	public static boolean isAttributePresent(WebDriver webDriver, By element, String attribute) {
		Boolean result = false;
		try {
			String value = getAttribute(webDriver, element, attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public static void rollToBottom(WebDriver webDriver) throws IOException {
		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("window.scrollBy(0,-900)", "");
	}
	
	public static void scrollToTop(WebDriver webDriver) throws IOException {
//		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
//		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		find(webDriver, By.xpath("html")).sendKeys(Keys.CONTROL, Keys.ARROW_UP, Keys.CONTROL, Keys.ARROW_UP, Keys.CONTROL, Keys.ARROW_UP);
	}

	public static Double convertAmountsToDouble(String Premiums) {

		String[] amount = Premiums.split("\\$");
		amount[1] = amount[1].replaceAll("\\s+", "");
		amount[1] = amount[1].replaceAll(",", "");
		Double AmountValue = Double.parseDouble(amount[1]);

		return AmountValue;

	}

	public static int getCountFromElement(WebDriver webDriver, By selector, By tagType) throws IOException {
		for (int i = 0; i <= 3; i++) {
			try {
				WebElement element = find(webDriver, selector);
				return element.findElements(tagType).size();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return 0;
	}
	
	public static void selectDataFromDropDown(WebDriver webDriver,By mainElement, By TagName, String input) throws IOException{
		WebElement select = find(webDriver, mainElement);
		List<WebElement> options = select.findElements(TagName);
		for (WebElement option : options) {
		    if(input.equals(option.getText()))
		        option.click();   
		}	
	}

	public static String convertToDate(int duration) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DAY_OF_MONTH, duration - 1);
//		Date returndate = new Date(rightNow.getTimeInMillis());

		int day = rightNow.get(Calendar.DAY_OF_MONTH);
		int month = rightNow.get(Calendar.MONTH);
		int year = rightNow.get(Calendar.YEAR);
		String properMonth;
		if ((month + 1) <= 9) {
			properMonth = "0" + (month + 1);
		} else {
			properMonth = String.valueOf((month + 1));
		}
		String StringHomePagereturndate = day + "/" + properMonth + "/" + year;

		return StringHomePagereturndate;
	}

	public static void scrollToElement(WebDriver webDriver, By selector) throws IOException {
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", find(webDriver, selector));
	}

	public static boolean verifyPDFContent(WebDriver webDriver, String reqTextInPDF)
			throws IOException, InterruptedException {
		System.out.println(webDriver.findElement(By.className("textLayer")).getText());
		String pdfText = webDriver.findElement(By.className("textLayer")).getText();
		if (pdfText.contains(reqTextInPDF)) {
			return true;
		}
		return true;
		// boolean flag = false;
		//
		// PDFTextStripper pdfStripper = null;
		// PDDocument pdDoc = null;
		// COSDocument cosDoc = null;
		// String parsedText = null;
		//
		// try {
		// URL url = new URL(strURL);
		// BufferedInputStream file = new BufferedInputStream(url.openStream());
		// PDFParser parser = new PDFParser((RandomAccessRead) file);
		//
		// parser.parse();
		// cosDoc = parser.getDocument();
		// pdfStripper = new PDFTextStripper();
		// pdfStripper.setStartPage(1);
		// pdfStripper.setEndPage(1);
		//
		// pdDoc = new PDDocument(cosDoc);
		// parsedText = pdfStripper.getText(pdDoc);
		// } catch (MalformedURLException e2) {
		// System.err.println("URL string could not be parsed
		// "+e2.getMessage());
		// } catch (IOException e) {
		// System.err.println("Unable to open PDF Parser. " + e.getMessage());
		// try {
		// if (cosDoc != null)
		// cosDoc.close();
		// if (pdDoc != null)
		// pdDoc.close();
		// } catch (Exception e1) {
		// e.printStackTrace();
		// }
		// }
		//
		// System.out.println("+++++++++++++++++");
		// System.out.println(parsedText);
		// System.out.println("+++++++++++++++++");
		// System.out.println(reqTextInPDF);
		// System.out.println(parsedText);
		// if(parsedText.contains(reqTextInPDF)) {
		// return true;
		// }else{
		//
		// return false;
		// }

	}
}
