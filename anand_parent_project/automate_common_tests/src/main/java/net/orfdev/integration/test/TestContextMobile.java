package net.orfdev.integration.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import net.orfdev.integration.test.util.OSUtil;

public class TestContextMobile {

	public static WebDriver driver;
	String hostname;
	
	 public static final String USERNAME = "petestorey1";
	  public static final String AUTOMATE_KEY = "bHtBpoqXxEP7UtL94Asp";
	  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";


	public TestContextMobile() throws MalformedURLException {
		if (driver == null) {
			if (OSUtil.isUnix())
				System.setProperty("webdriver.firefox.bin", "/opt/firefox46/firefox");
			else {
				if (driver == null) {
					try
					{
					    InetAddress addr;
					    addr = InetAddress.getLocalHost();
					    hostname = addr.getHostName();
					    System.out.println("hostname   "+hostname);
					}
					catch (UnknownHostException ex)
					{
					    System.out.println("Hostname can not be resolved");
					}
					if(hostname.equalsIgnoreCase("1COVERTEST")){
					 DesiredCapabilities caps = new DesiredCapabilities();
					    caps.setCapability("browser", "Safari");
					    caps.setCapability("browserstack.debug", "true");
					    caps.setCapability("build", "First build");
					    caps.setCapability("realMobile", true);
					    caps.setCapability("device", "iPhone 7 Plus");
					    caps.setCapability("browserstack.appium_version", "1.6.3");
//					    caps.setCapability("os_version", "6.0");
					    
					    driver = new RemoteWebDriver(new java.net.URL(URL), caps);	
					
					}else if(hostname.equalsIgnoreCase("1COVER90")){
						 DesiredCapabilities caps = new DesiredCapabilities();
						    caps.setCapability("browser", "Safari");
						    caps.setCapability("browserstack.debug", "true");
						    caps.setCapability("build", "First build");
						    caps.setCapability("realMobile", true);
						    caps.setCapability("device", "iPhone 7 Plus");
						    caps.setCapability("browserstack.appium_version", "1.6.3");
						    driver = new RemoteWebDriver(new java.net.URL(URL), caps);	
						    
//
//						File pathToBinary = new File(
//								"C:\\Users\\Anand Jois\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
//						FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
//						FirefoxProfile ffprofile = new FirefoxProfile();
//						ffprofile.setPreference("general.useragent.override",
//								"Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
//						driver = new FirefoxDriver(ffBinary, ffprofile);
//						driver.manage().window().setSize(new Dimension(750, 920));
					}else{
						 DesiredCapabilities caps = new DesiredCapabilities();
						    caps.setCapability("browser", "Safari");
						    caps.setCapability("browserstack.debug", "true");
						    caps.setCapability("build", "First build");
						    caps.setCapability("realMobile", true);
						    caps.setCapability("device", "iPhone 7 Plus");
						    caps.setCapability("browserstack.appium_version", "1.6.3");
					}

				}
			}

		}
	}

	

	public WebDriver getDriver() throws IOException {
		if (Objects.isNull(driver))
			throw new IOException("Cannot load web driver.");
		return driver;
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

}
