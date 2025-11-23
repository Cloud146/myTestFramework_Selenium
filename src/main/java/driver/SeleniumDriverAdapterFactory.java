package driver;

import capabilities.SeleniumBrowserCapabilities;
import driverAdapter.DriverAdapter;
import driverAdapter.DriverAdapterFactory;
import org.openqa.selenium.WebDriver;

/**
 * Фабрика для создания {@link SeleniumDriverAdapter}.
 * Реализует SPI-контракт {@link DriverAdapterFactory} и регистрируется
 * через ServiceLoader (META-INF/services/driver.DriverAdapterFactory).
 */
public class SeleniumDriverAdapterFactory implements DriverAdapterFactory {

    @Override
    public String getEngineName() {
        return "selenium";
    }

    @Override
    public DriverAdapter create() {
        SeleniumBrowserCapabilities capabilities = new SeleniumBrowserCapabilities();
        WebDriver webDriver = new SeleniumBrowserCapabilities().createDriver();
        DriverManager.setDriver(webDriver);
        return new SeleniumDriverAdapter(webDriver);
    }
}
