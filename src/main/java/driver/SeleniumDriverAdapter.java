package driver;

import locator.Locator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.LocatorReader;

import java.util.List;
import java.util.stream.Collectors;

public class SeleniumDriverAdapter implements DriverAdapter {

    private final WebDriver driver;

    public SeleniumDriverAdapter(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public ElementHandle find(Locator locator) {
        WebElement element = driver.findElement(LocatorReader.toBy(locator));
        return new SeleniumElementHandle(element);
    }

    @Override
    public List<ElementHandle> findAll(Locator locator) {
        return driver.findElements(LocatorReader.toBy(locator))
                .stream()
                .map(SeleniumElementHandle::new)
                .collect(Collectors.toList());
    }
}
