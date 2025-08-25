package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Утилита для управления WebDriver (паттерн Singleton).
 * По умолчанию поднимает ChromeDriver, но можно доработать под разные браузеры.
 */
public class DriverManager {
    private static WebDriver driver;

    /**
     * Возвращает активный WebDriver или создаёт новый, если он ещё не создан.
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            // Автоматически скачает/обновит драйвер под текущий Chrome
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            // Можно добавить любые нужные флаги, например:
            // options.addArguments("--headless=new");
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    /**
     * Закрывает браузер и очищает ссылку на WebDriver.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
