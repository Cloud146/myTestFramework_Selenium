package driver;

import org.openqa.selenium.WebDriver;
import java.util.Map;

/**
 * Контракт для фабрик драйверов браузеров.
 */
public interface BrowserDriverFactory {

    /**
     * Имя браузера (должно совпадать с browser в selenium_runtime.yaml).
     */
    String getBrowserName();

    /**
     * Создаёт WebDriver на основе selenium_runtime.yaml.
     *
     * @param config настройки из selenium_runtime.yaml
     * @return готовый WebDriver
     */
    WebDriver createDriver(Map<String, Object> config);
}
