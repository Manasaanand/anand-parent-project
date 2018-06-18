package net.orfdev.integration.test.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.StringUtils;

import cucumber.api.Scenario;
import net.orfdev.integration.test.TestContext;
import net.orfdev.integration.test.TestContextMobileBrowserStack;

public class Navigator {

	private final static Logger log = LogManager.getLogger(null, null);

	 String winHandleBefore;
	public LocalDate today;
	private WebDriver webDriver;
	public static boolean failureStatus = false;

	public Navigator(TestContext testContext) throws IOException {
		this.webDriver = testContext.getDriver();

	}

	public Navigator(TestContextMobileBrowserStack testContextMobile) throws IOException {
		this.webDriver = testContextMobile.getDriver();

	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public String getDateOfBirth(int customerAge) {
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

	public void enterDateOfBirth(By selector, String dateOfBirth) throws IOException {
		Actions action = new Actions(webDriver);
		action.sendKeys(Keys.DELETE);
		action.sendKeys(find(selector), dateOfBirth).build().perform();
	}

	public boolean waitForPopUpToDisplay() throws IOException {
		{
			int i = 0;
			while (i++ < 5) {
				try {
					Alert alert = webDriver.switchTo().alert();
					return true;
				} catch (NoAlertPresentException e) {
					continue;
				}
			}
		}
		return false;

	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public String getURL() {
		return webDriver.getCurrentUrl();
	}

	public String getDeviceType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadPage(String URL) throws IOException {
		try {
			webDriver.get(URL);
		} catch (UnreachableBrowserException e) {
			TestContext.driver = null;
//			TestContext testContext = new TestContext();
			TestContext.driver.get(URL);
		} catch (WebDriverException e) {
			TestContext.driver = null;
			TestContext testContext = new TestContext();
			testContext.driver.get(URL);
		}
		

	}

	public String getText(By selector) throws IOException {
//		for (int i = 0; i <= 3; i++) {
//			try {
				waitForElementVisible(selector, 10);
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//			}
//		}
		return find(selector).getText();
	}

	public void reducewebsiteSize() throws IOException {
		WebElement html = find(By.tagName("html"));
		html.sendKeys(Keys.chord(Keys.CONTROL, "-", "-", "-", "-"));
	}

	public void clearAndEnterTextUsingJavaScript(By cssSelector, String input) throws IOException {
		find(cssSelector).clear();
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		WebElement el = find(cssSelector);
		js.executeScript("arguments[0].value = arguments[1];", el, input);

	}

	public void clickfromFrame(By frame, By selector) {
		WebElement Frame = webDriver.findElement(frame);
		Frame.findElement(selector).click();
	}

	public double roundDecimals(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public Double convertStringToDouble(String labelPrice) {
		String[] justPrice = labelPrice.split("\\$ ");
		// int planitem = Integer.parseInt(justPrice[1]);
		Double subtotal = Double.parseDouble(justPrice[1]);
		return subtotal;
	}

	public Double convertStringToDoubleWithoutSpace(String labelPrice) {
		String[] justPrice = labelPrice.split("\\$");

		Double subtotal = Double.parseDouble(justPrice[1]);
		return subtotal;
	}

	public int convertStringToIntegerWithoutSpace(String labelPrice) {
		String[] justPrice = labelPrice.split("\\$");

		int subtotal = Integer.parseInt(justPrice[1]);
		return subtotal;
	}

	public void waitUntilElementIsNotVisible(By selector, int timeinSeconds) throws IOException {
		WebDriverWait wait = new WebDriverWait(webDriver, timeinSeconds);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(selector));
	}

	public void enterDateofBirth(By selector, String date) throws IOException {
		Actions action = new Actions(webDriver);
		action.sendKeys(Keys.DELETE);
		action.sendKeys(find(selector), date).build().perform();

	}

	public void clickOptionFromDropDown(By dropDownElement, By tagName, int count) throws IOException {
		WebElement dropDown = find(dropDownElement);
		dropDown.findElements(tagName).get(count).click();
	}

	public void sendKeys(By selector, Keys data) throws IOException {
		find(selector).sendKeys(data);
	}

	public WebElement waitForElement(By selector, Integer waitTimeInSeconds) throws IOException {
		WebElement elementWaitFor = find(selector);
		if (waitTimeInSeconds == null) {
			waitTimeInSeconds = 5;
		}
		WebDriverWait wait = new WebDriverWait(webDriver, waitTimeInSeconds);
		return wait.until(ExpectedConditions.visibilityOf(elementWaitFor));
	}

	public void waitUntillStringChanges(By selector, Integer waitTimeInSeconds, String input) throws IOException {
		WebDriverWait wait = new WebDriverWait(webDriver, waitTimeInSeconds);
		wait.until(ExpectedConditions.textToBe(selector, input));

	}

	public void waitUntillStringContains(By selector, Integer waitTimeInSeconds, String input) throws IOException {
		WebDriverWait wait = new WebDriverWait(webDriver, waitTimeInSeconds);
		wait.until(ExpectedConditions.textToBePresentInElement(find(selector), input));
	}

	public void waitUntillStringContains(WebElement element, Integer waitTimeInSeconds, String input) {
		WebDriverWait wait = new WebDriverWait(webDriver, waitTimeInSeconds);
		wait.until(ExpectedConditions.textToBePresentInElement(element, input));
	}

	public List<WebElement> waitForElementVisible(By selector, Integer waitTimeInSeconds) throws IOException {

		WebDriverWait wait = new WebDriverWait(webDriver, waitTimeInSeconds);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));

	}

	public boolean waitForElementDisplay(By selector) throws IOException {
		{
			int i = 0;
			while (i++ < 5) {
				try {
					WebDriverWait wait = new WebDriverWait(webDriver, 5);
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
					return true;
				} catch (NoAlertPresentException e) {
					continue;
				}
			}
		}
		return false;

	}

	public void waitForElementIsPresent(By selector, Integer waitTimeInSeconds) throws IOException {
		WebDriverWait wait = new WebDriverWait(webDriver, waitTimeInSeconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(selector));
	}

	public void waitForElementToInvisible(By selector) throws IOException {
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(selector));

	}

	public void refresh() throws IOException {
		webDriver.navigate().refresh();
	}

	public void switchToAnotherWindow() {
		try {

			winHandleBefore = webDriver.getWindowHandle();
			for (String winHandle : webDriver.getWindowHandles()) {
				webDriver.switchTo().window(winHandle);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		ArrayList<String> multipleTabs = new ArrayList<String>(getWebDriver().getWindowHandles());
		System.out.println(multipleTabs.size());
	}

	public void switchBackToParentWindow(String mainWindowHandle) {
		webDriver.switchTo().window(mainWindowHandle);

	}

	public String getDateOfBirth(int customerAge, LocalDate today, int offset) {
		return NavigatorDateUtil.toShortFormat(today.minusYears(customerAge).minusDays(offset));
	}

	public void clickByAction(By selector) throws IOException {
		Actions action = new Actions(webDriver);
		action.moveToElement(find(selector)).click().perform();
	}

	public boolean elementExist(By selector) throws IOException {
		return find(selector).isEnabled();

	}

	public String getAttribute(By selector, String attribute) throws IOException {
		return find(selector).getAttribute(attribute);
	}

	public boolean elementDisplay(By selector) throws IOException {
		return findAll(selector).size() != 0;
	}

	public boolean isDisplayed(By selector) throws IOException {
		return findAll(selector).stream().anyMatch(element -> element.isDisplayed());
	}

	public List<WebElement> findAll(By selector) throws IOException {
		return webDriver.findElements(selector);
	}

	public Double convertPremium(By Premiums) throws IOException {

		String PremiumValue = getText(Premiums);
		String[] Amount = PremiumValue.split("\\$");
		Double AmountValue = Double.parseDouble(Amount[1]);
		return AmountValue;

	}

	public Date StringToDate(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.parse(date);
	}

	public void clickBasedOnCount(By selector, int i) throws IOException {
		webDriver.findElements(selector).get(i).click();

	}

	public Double convertToDouble(String Amount) {
		String[] AmountsToBeConverted = Amount.split("\\$");
		Double ConvertedAmount = Double.parseDouble(AmountsToBeConverted[1]);

		return ConvertedAmount;
	}

	public void takeScreenShot(Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			failureStatus = true;
			byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}

	}

	public void ClearBrowserCache() throws IOException {
		webDriver.manage().deleteAllCookies();
	}

	public void close() throws IOException {
		webDriver.close();
	}

	public void moveToElement(By selector) throws IOException {
		Actions action = new Actions(webDriver);
		WebElement element = find(selector);
		action.moveToElement(element).build().perform();

	}

	public int getCount(String coverID) throws IOException {
		waitForElementVisible(By.id(coverID), 5);
		return findAll(By.id(coverID)).size();

	}

	public String getTextFromTextBox(By selector) throws IOException {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		return (String) ((js).executeScript("return arguments[0].value", find(selector)));
	}

	public void goToPreviousPage() throws IOException {
		webDriver.navigate().back();
	}

	public void selectData(By selector, String arg1) throws IOException {
		find(selector).sendKeys(arg1);
	}

	public void verifyPageTitle(String expectedTitle) throws IOException {

		Assert.assertEquals(expectedTitle, webDriver.getTitle());
	}

	public boolean isAttributePresent(By element, String attribute) {
		Boolean result = false;
		try {
			String value = getAttribute(element, attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public void rollToBottom() throws IOException {
		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("window.scrollBy(0, -900)", "");
	}

	public Double convertAmountsToDouble(String Premiums) {

		String[] amount = Premiums.split("\\$");
		amount[1] = amount[1].replaceAll("\\s+", "");
		amount[1] = amount[1].replaceAll(",", "");
		Double AmountValue = Double.parseDouble(amount[1]);

		return AmountValue;

	}

	public int getCountFromElement(By selector, By tagType) throws IOException {
		WebElement element = find(selector);
		return element.findElements(tagType).size();
	}

	public void selectDataFromDropDown(By mainElement, By TagName, String input) throws IOException {
		WebElement select = find(mainElement);
		List<WebElement> options = select.findElements(TagName);
		for (WebElement option : options) {
			if (input.equals(option.getText()))
				option.click();
		}
	}

	public boolean verifyPDFContent(String reqTextInPDF) throws IOException, InterruptedException {
		System.out.println(webDriver.findElement(By.className("textLayer")).getText());
		String pdfText = webDriver.findElement(By.className("textLayer")).getText();
		if (pdfText.contains(reqTextInPDF)) {
			return true;
		}
		return true;

	}

	public void maximize() {
		webDriver.manage().window().maximize();
	}

	/* find */

	public WebElement find(By selector) throws IOException {
		return find(selector, 5);
	}

	public WebElement find(By selector, int wait) throws IOException {
		waitForElementVisible(selector, wait);
		return webDriver.findElement(selector);
	}

	/* Scroll to element */

	public void scrollToElement(By selector) throws IOException {
		// ((JavascriptExecutor)
		// webDriver).executeScript("arguments[0].scrollIntoView();",
		// find(selector));
		scrollToElement(find(selector), true);
	}

	private void scrollToElement(WebElement element, boolean scrollUp) {
		String scrollScript = String.format("arguments[0].scrollIntoView(%b);", scrollUp);
		log.debug(String.format("About to excute javascript: %s", scrollScript));
		((JavascriptExecutor) webDriver).executeScript(scrollScript, element);
	}

	/* Select component */

	public boolean select(By selector, String value) throws IOException {
		WebElement element = find(selector, 10);
		boolean selected = select(element, value);
		if (!selected) {
			scrollToElement(element, false);
			selected = select(element, value);
		}

		if (!selected) {
			scrollToElement(element, true);
			selected = select(element, value);
		}

		if (!selected)
			log.error("Failed to selected element %s");

		return selected;
	}

	private boolean select(WebElement element, String value) throws IOException {
		boolean selected = false;

		try {
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(value);
			selected = true;
		} catch (WebDriverException wde) {
			selected = false;
		}

		return selected;
	}

	/* Click handler */

	public boolean click(By selector) throws IOException {
		boolean clicked = false;
		for (int i = 0; i <= 3; i++) {
			try {
				waitForElementIsPresent(selector, 5);
				WebElement element = find(selector, 10);

				try {
					Actions action = new Actions(webDriver);
					action.moveToElement(element).build().perform();

					clicked = click(element);
					return true;
				} catch (MoveTargetOutOfBoundsException mte) {
					clicked = false;
				}

				// Try to scroll down first as scrolling up might hide stuff
				// behind the
				// menu bars,
				// and throw web driver exceptions, or worse not, and click on a
				// menu
				// bar option.
				if (!clicked) {
					scrollToElement(element, false);
					clicked = click(element);
					return true;
				}

				if (!clicked) {
					scrollToElement(element, true);
					clicked = click(element);
					return true;
				}

				if (!clicked)
					log.error("Failed to scroll down/up to click element %s");

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return clicked;
	}

	private boolean click(WebElement element) {
		try {
			element.click();
			return true;
		} catch (WebDriverException wde) {
			log.trace("Found an exception when trying to click", wde);
			return false;
		}
	}

	/* Enter text */

	public boolean enterText(By selector, String value) throws IOException {
		return enterText(selector, value, value);
	}

	public boolean enterText(By selector, String value, String expected) throws IOException {

		boolean entered = false;
		WebElement element = find(selector, 10);

		// try and enter data without scrolling
		try {
			Actions action = new Actions(webDriver);
			action.moveToElement(element).build().perform();
			entered = send(element, value, expected);
			return true;
		} catch (WebDriverException wde) {
			entered = false;
			
		}

		// click on the element, will attempt to scroll to the element
		// then enter data
		if (!entered) {
			boolean clicked = click(selector);
			if (clicked)
				entered = send(element, value, expected);
			else
				log.error("Failed to click element %s");
		}

		if (!entered)
			log.error(String.format("clearAndEnterTextAfterScroll sending %s into %s result: %b", value,
					find(selector).toString(), entered));

		return entered;
	}

	public boolean send(WebElement webElement, String value, String expected) {
		webElement.click();
		webElement.clear();
		webElement.sendKeys(value);

		if (!webElement.getAttribute("value").equals(expected)) {
			if (StringUtils.hasText(webElement.getAttribute("id"))) {
				webElement.click();
				webElement.clear();
				sendByJavascript(webElement, value);
			}
		}

		if (!webElement.getAttribute("value").equals(expected)) {
			webElement.click();
			webElement.clear();
			sendByChar(webElement, value);
		}

		if (!webElement.getAttribute("value").equals(expected)) {
			clearText(webElement);
			sendByChar(webElement, value);
		}

		return webElement.getAttribute("value").equals(expected);
	}

	private void sendByJavascript(WebElement webElement, String value) {
		String script = String.format("document.getElementById('%s').setAttribute('value', '%s')",
				webElement.getAttribute("id"), value);
		log.debug(String.format("Sending keys into element with javascript: %s", script));

		((JavascriptExecutor) webDriver).executeScript(script);
	}

	private void sendByChar(WebElement webElement, String value) {
		for (char c : value.toCharArray()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.trace("sendKeyIntoElement by character", e);
			}

			String cs = new StringBuilder().append(c).toString();
			if (cs.equals(" "))
				webElement.sendKeys(Keys.SPACE);
			else
				webElement.sendKeys(cs);
		}
	}

	private void clearText(WebElement webElement) {
		while (!webElement.getAttribute("value").equals("")) {
			webElement.click();
			webElement.sendKeys(Keys.CONTROL + "a");
			webElement.sendKeys(Keys.DELETE);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.trace("sendKeyIntoElement character delete", e);
			}
		}
	}

	public void windowMaximise() {
		webDriver.manage().window().maximize();

	}

	public void reducewebsiteSize(WebDriver webDriver) throws IOException {
		WebElement html = find(By.tagName("html"));
		html.sendKeys(Keys.chord(Keys.CONTROL, "-", "-", "-", "-"));
	}

	public int[] convertStringArrayToIntArray(String ages) {
		String[] age = ages.split(",");
		int[] intarray = new int[age.length];
		int i = 0;
		for (String str : age) {
			try {
				intarray[i] = Integer.parseInt(str);
				i++;
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Not a number: " + str + " at index " + i, e);
			}
		}

		return intarray;

	}

	public String convertToDate(int duration) {
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

	public void waitforLoaderToDisappear() {
		WebDriverWait wait = new WebDriverWait(webDriver, 5);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loader")));
		
	}

	public void scrolToTop() {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		 js.executeScript("window.scrollBy(0,-250)", "");
		
	}

	public void enterText(By address, Keys key) {
		webDriver.findElement(address).sendKeys(key);
		
	}

}
