package selenium_adapters.interaction;

import driverAdapter.adapter_api_contracts.interaction.MouseAdapter;
import FileUtils.PageReader;
import locator.Locator;
import logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import CurrentPageUtils.CurrentPageHolder;

import java.util.Objects;

import static utils.LocatorReader.toBy;

public class SeleniumMouseAdapter implements MouseAdapter {

    private final WebDriver driver;
    private final CurrentPageHolder pageHolder;

    public SeleniumMouseAdapter(WebDriver driver, CurrentPageHolder pageHolder) {
        this.driver = Objects.requireNonNull(driver, "driver");
        this.pageHolder = pageHolder;
    }

    @Override
    public void clickElement(String elementName) {
        PageReader currentPage = pageHolder.get();
        Locator locator = currentPage.getLocator(elementName);
        By by = toBy(locator);
        driver.findElement(by).click();
    }

    @Override
    public void doubleClickElement(String elementName) {
        PageReader currentPage = pageHolder.get();
        Locator locator = currentPage.getLocator(elementName);
        By by = toBy(locator);
        WebElement element = driver.findElement(by);

        new Actions(driver)
                .doubleClick(element)
                .perform();
    }

    @Override
    public void rightClickElement(String elementName) {
        PageReader currentPage = pageHolder.get();
        Locator locator = currentPage.getLocator(elementName);
        By by = toBy(locator);
        WebElement element = driver.findElement(by);

        new Actions(driver)
                .contextClick(element)
                .perform();
    }
}
