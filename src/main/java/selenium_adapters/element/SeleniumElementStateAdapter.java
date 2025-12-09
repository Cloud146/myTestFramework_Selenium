package selenium_adapters.element;

import CurrentPageUtils.CurrentPageHolder;
import FileUtils.PageReader;
import driverAdapter.adapter_api_contracts.element.ElementStateAdapter;
import locator.Locator;
import org.openqa.selenium.*;
import utils.LocatorReader;

public class SeleniumElementStateAdapter implements ElementStateAdapter {

    private final WebDriver driver;
    private final CurrentPageHolder pageHolder;

    public SeleniumElementStateAdapter(WebDriver driver, CurrentPageHolder pageHolder) {
        this.driver = driver;
        this.pageHolder = pageHolder;
    }

    @Override
    public boolean isElementDisplayed(String elementName) {
        try {
            WebElement element = driver.findElement(toBy(elementName));
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    @Override
    public boolean isElementPresent(String elementName) {
        try {
            return !driver.findElements(toBy(elementName)).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isElementEnabled(String elementName) {
        try {
            WebElement element = driver.findElement(toBy(elementName));
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Невозможно проверить состояние 'Active/Enabled': элемент не найден [" + elementName + "]");
        }
    }

    private By toBy(String elementName) {
        PageReader currentPage = pageHolder.get();
        Locator locator = currentPage.getLocator(elementName);

        return LocatorReader.toBy(locator);
    }
}