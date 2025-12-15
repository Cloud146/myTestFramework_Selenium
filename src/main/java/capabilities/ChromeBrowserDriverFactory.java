package capabilities;

import browsers.BrowserConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;

public class ChromeBrowserDriverFactory extends AbstractChromiumFactory  {

    private static final Logger log = Log.get(ChromeBrowserDriverFactory.class);

    @Override
    public String getBrowserName() {
        return "chrome";
    }

    @Override
    public WebDriver createDriver(BrowserConfig config) {
        setupDriverBinary(config, "webdriver.chrome.driver", WebDriverManager.chromedriver());

        ChromeOptions options = new ChromeOptions();
        configureCommonOptions(options, config);

        WebDriver driver = new ChromeDriver(options);

        applyWindowSettings(driver, config);

        logBrowserInformation(driver);
        return driver;
    }
}
