package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

/**
 * Набор базовых действий Selenium.
 * Работает с любым WebDriver, переданным в конструктор.
 * Использует fresh lookup — поиск элемента при каждом действии.
 */
public class SeleniumActions {

    private final WebDriver driver;

    /**
     * @param driver активный экземпляр WebDriver (не null)
     */
    public SeleniumActions(WebDriver driver) {
        this.driver = Objects.requireNonNull(driver, "driver");
    }

    /**
     * Клик по элементу, найденному по локатору.
     * @param locator Selenium By
     */
    public void click(By locator) {
        driver.findElement(locator).click();
    }

    /**
     * Ввод текста в поле ввода (предварительно очищает).
     * @param locator Selenium By
     * @param text текст для ввода
     */
    public void type(By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Получение текста элемента.
     * @param locator Selenium By
     * @return видимый текст
     */
    public String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    /**
     * Поиск первого элемента по локатору.
     * @param locator Selenium By
     * @return найденный WebElement
     */
    public WebElement find(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Поиск всех элементов по локатору.
     * @param locator Selenium By
     * @return список найденных элементов (возможно пустой)
     */
    public List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }
}