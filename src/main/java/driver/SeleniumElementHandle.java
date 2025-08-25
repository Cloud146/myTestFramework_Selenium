package driver;

import org.openqa.selenium.WebElement;

public class SeleniumElementHandle implements ElementHandle {

    private final WebElement element;

    public SeleniumElementHandle(WebElement element) {
        this.element = element;
    }

    @Override
    public void click() {
        element.click();
    }

    @Override
    public void sendKeys(CharSequence... text) {
        element.sendKeys(text);
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    @Override
    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    @Override
    public void clear() {
        element.clear();
    }
}
