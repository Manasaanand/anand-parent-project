package net.orfdev.integration.test.ios.ui.scenarios;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(strict = false, features = "classpath:net/orfdev/integration/test/ios/ui/scenarios",
		// plugin = "html:../cucumber/cucumber-html-reports/downunderau/embeddings",
		// format = {
		// "pretty", "html:../cucumber/site/downunderau/cucumber-pretty",
		// "json:" + TestDictionary.DOWNUNDER_AU_JSON },
		tags = { "~@ignore" }, monochrome = true)
public class TestRunner {

}
