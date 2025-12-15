package driver;

import browsers.BrowserConfig;
import browsers.BrowserFactory;
import org.openqa.selenium.WebDriver;

public interface BrowserDriverFactory extends BrowserFactory<WebDriver> {

    String getBrowserName();

    WebDriver createDriver(BrowserConfig config);
}
