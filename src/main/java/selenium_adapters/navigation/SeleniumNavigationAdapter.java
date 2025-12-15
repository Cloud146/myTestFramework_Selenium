package selenium_adapters.navigation;

import driverAdapter.adapter_api_contracts.navigation.NavigationAdapter;
import org.openqa.selenium.WebDriver;

public class SeleniumNavigationAdapter implements NavigationAdapter {

    private final WebDriver driver;

    public SeleniumNavigationAdapter(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void openURL(String url) {
        driver.get(url);
    }
}
