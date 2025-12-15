package selenium_adapters.element;

import driverAdapter.adapter_api_contracts.element.TextAdapter;
import FileUtils.PageReader;
import locator.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import CurrentPageUtils.CurrentPageHolder;
import java.util.Objects;
import static utils.LocatorReader.toBy;

public class SeleniumTextAdapter implements TextAdapter {

    private final WebDriver driver;
    private final CurrentPageHolder pageHolder;

    public SeleniumTextAdapter(WebDriver driver, CurrentPageHolder pageHolder) {
        this.driver = Objects.requireNonNull(driver, "driver");
        this.pageHolder = pageHolder;
    }

    @Override
    public String getTextFromElement(String elementName){
        PageReader currentPage = pageHolder.get();
        Locator locator = currentPage.getLocator(elementName);
        By by = toBy(locator);
        return driver.findElement(by).getText();
    }
}
