package utils;

import locator.Locator;
import locator.LocatorType;
import org.openqa.selenium.By;

/**
 * Конвертер универсального локатора (core.locator.Locator) в Selenium By.
 */
public final class LocatorReader {
    private LocatorReader() {}

    /**
     * Преобразует core.Locator в Selenium By.
     * @param locator локатор из core
     * @return объект org.openqa.selenium.By
     * @throws IllegalArgumentException если тип локатора не поддержан
     */
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

            // Мобильные/платформенные локаторы для других движков — не поддерживаются здесь
            case ACCESSIBILITY_ID, ANDROID_UIAUTOMATOR, IOS_PREDICATE, IOS_CLASS_CHAIN ->
                // Для WebDriver напрямую нет, но можно имитировать как XPATH или кастом
//                    By.xpath("//*[@content-desc='" + value + "']");
                throw new IllegalArgumentException("Unsupported locator type for Selenium: " + type);
        };
    }
}
