package net.orfdev.integration.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class TestContextAndroid {

	public static AndroidDriver<AndroidElement> driver;
	public static String hostname;
	String[] dayOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	String exepathsBeg = "C:\\Users\\";
	String exepathsEnd = "\\Documents\\development\\engage-test\\engage-test-common\\exes\\";
	String username;

	public TestContextAndroid() throws IOException {
		File app = new File("C:\\workspace\\test-automation\\AndroidAppium\\app\\ApiDemos-debug.apk");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "100");
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);

	}

	public WebDriver getDriver() throws IOException {
		if (Objects.isNull(driver))
			throw new IOException("Cannot load web driver.");
		return driver;
	}
}
