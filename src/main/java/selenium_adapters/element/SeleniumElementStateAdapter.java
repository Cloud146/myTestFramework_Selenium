package selenium_adapters.element;

import CurrentPageUtils.CurrentPageHolder;
import FileUtils.PageReader;
import driverAdapter.adapter_api_contracts.element.ElementStateAdapter;
import locator.Locator;
import org.openqa.selenium.*;

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

        return switch (locator.getType()) {
            case ID -> By.id(locator.getValue());
            case XPATH -> By.xpath(locator.getValue());
            case CSS -> By.cssSelector(locator.getValue());
            case CLASS_NAME -> By.className(locator.getValue());
            case NAME -> By.name(locator.getValue());
            case TAG_NAME -> By.tagName(locator.getValue());
            case LINK_TEXT -> By.linkText(locator.getValue());
            case PARTIAL_LINK_TEXT -> By.partialLinkText(locator.getValue());
            default -> throw new IllegalArgumentException("Unsupported locator type: " + locator.getType());
        };
    }
}