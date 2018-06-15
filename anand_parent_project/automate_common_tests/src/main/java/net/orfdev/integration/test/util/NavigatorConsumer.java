package net.orfdev.integration.test.util;

import java.io.IOException;
import java.util.function.Consumer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;

public class NavigatorConsumer {

	private final static Logger log = LogManager.getLogger(null, null);
	
	public static void retry(Consumer<Navigator> consumer, Navigator nav, int retries, String msg) throws IOException {
		boolean loaded = false;
		int attempt = 0;
		while (!loaded && attempt < retries) {
			try {
				consumer.accept(nav);
				loaded = true;
			}
			catch (StaleElementReferenceException e) {
				log.debug(msg, e);
				attempt++;
				if (attempt >= retries)
					throw e;
			}
		}
	}
}
