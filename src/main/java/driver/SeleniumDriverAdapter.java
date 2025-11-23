package driver;

import CurrentPageUtils.PageContext;
import driverAdapter.DriverAdapter;
import org.openqa.selenium.WebDriver;
import CurrentPageUtils.CurrentPageHolder;
import selenium_adapters.element.SeleniumTextAdapter;
import selenium_adapters.interaction.SeleniumMouseAdapter;
import selenium_adapters.navigation.SeleniumNavigationAdapter;

import java.util.Objects;

/**
 * Реализация DriverAdapter для Selenium.
 */
public class SeleniumDriverAdapter implements DriverAdapter {

    private final WebDriver driver;

    private final SeleniumMouseAdapter mouseAdapter;
    private final SeleniumNavigationAdapter navigationAdapter;
    private final SeleniumTextAdapter textAdapter;

    public SeleniumDriverAdapter(WebDriver driver) {
        this.driver = Objects.requireNonNull(driver, "driver");
        CurrentPageHolder pageHolder = PageContext.getHolder();

        this.mouseAdapter = new SeleniumMouseAdapter(driver, pageHolder);
        this.navigationAdapter = new SeleniumNavigationAdapter(driver);
        this.textAdapter = new SeleniumTextAdapter(driver, pageHolder);
    }

    @Override
    public void clickElement(String elementName){
        mouseAdapter.clickElement(elementName);
    }

    @Override
    public void doubleClickElement(String elementName) {
        mouseAdapter.doubleClickElement(elementName);
    }

    @Override
    public void rightClickElement(String elementName) {
        mouseAdapter.rightClickElement(elementName);
    }

    @Override
    public void openURL(String url){
        navigationAdapter.openURL(url);
    }

    @Override
    public String getTextFromElement(String elementName){
        return textAdapter.getTextFromElement(elementName);
    }

    @Override
    public void close() {
        driver.quit();
    }
}