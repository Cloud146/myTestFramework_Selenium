package utils;

import locator.Locator;
import locator.LocatorType;
import org.openqa.selenium.By;

public final class LocatorReader {
    private LocatorReader() {}

    public static By toBy(Locator locator) {
        LocatorType type = locator.getType();
        String value = locator.getValue();
        return switch (type) {
            case ID -> By.id(value);
            case NAME -> By.name(value);
            case XPATH -> By.xpath(value);
            case CSS -> By.cssSelector(value);
            case CLASS_NAME -> By.className(value);
            case TAG_NAME -> By.tagName(value);
            case LINK_TEXT -> By.linkText(value);
            case PARTIAL_LINK_TEXT -> By.partialLinkText(value);

            case ACCESSIBILITY_ID, ANDROID_UIAUTOMATOR, IOS_PREDICATE, IOS_CLASS_CHAIN ->
                throw new IllegalArgumentException("Unsupported locator type for Selenium: " + type);
        };
    }
}
