package capabilities;

import browsers.BrowserConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;

public class EdgeBrowserDriverFactory extends AbstractChromiumFactory {

    private static final Logger log = Log.get(EdgeBrowserDriverFactory.class);

    @Override
    public String getBrowserName() {
        return "edge";
    }

    @Override
    public WebDriver createDriver(BrowserConfig config) {
        setupDriverBinary(config, "webdriver.edge.driver", WebDriverManager.edgedriver());

        EdgeOptions options = new EdgeOptions();
        configureCommonOptions(options, config);

        WebDriver driver = new EdgeDriver(options);

        applyWindowSettings(driver, config);

        logBrowserInformation(driver);
        return driver;
    }
}
