package net.orfdev.integration.test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import net.orfdev.integration.test.util.OSUtil;

/**
 * Unix requires Firefox46 with the executable located at the file path
 * specified in the TestContext constructor. Files should be available at
 * https://ftp.mozilla.org/pub/firefox/releases/46.0/
 */
public class TestContext {

	// private static WebDriver driver;
	public static WebDriver driver;
	public static String hostname;
	String[] dayOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	String exepathsBeg = "C:\\Users\\";
	String exepathsEnd = "\\Documents\\development\\engage-test\\engage-test-common\\exes\\";
	String username;

	public TestContext() throws IOException {
		if (OSUtil.isUnix())
			System.setProperty("webdriver.firefox.bin", "/opt/firefox46/firefox");
		else {
			if (driver == null) {

				try {
					InetAddress addr;
					addr = InetAddress.getLocalHost();
					hostname = addr.getHostName();
					System.out.println("hostname   " + hostname);
					if (hostname.equalsIgnoreCase("1COVERTEST")) {
						exepathsEnd = "\\Documents\\development\\exes\\";
						username = "iTest";
					} else
						username = "Anand Jois";

				} catch (UnknownHostException ex) {
					System.out.println("Hostname can not be resolved");
				}
				if (hostname.equalsIgnoreCase("1COVERTEST")) {
					FirefoxProfile profile = new FirefoxProfile();
					profile.setPreference("general.useragent.override", "ui_test_runner");
					DesiredCapabilities capabilites = new DesiredCapabilities();
					capabilites.setCapability(FirefoxDriver.PROFILE, profile);
					capabilites.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
							UnexpectedAlertBehaviour.ACCEPT);
					driver = new FirefoxDriver(capabilites);
				} else if (hostname.equalsIgnoreCase("1COVER90")) {
					File pathToBinary = new File("C:\\Users\\Anand Jois\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
					FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
					FirefoxProfile firefoxProfile = new FirefoxProfile();
					driver = new FirefoxDriver();

				}
				if (isAlertPresents()) {
					driver.switchTo().alert().accept();
					driver.switchTo().defaultContent();
				}

			}
		}

	}

	public boolean isAlertPresents() {
		try {
			driver.switchTo().alert();
			return true;
		} // try
		catch (Exception e) {
			return false;
		} // catch
	}

	public WebDriver getDriver() throws IOException {
		if (Objects.isNull(driver))
			throw new IOException("Cannot load web driver.");
		return driver;
	}

}
