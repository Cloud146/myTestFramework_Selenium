package capabilities;

import config.RuntimeReader;
import driver.BrowserDriverFactory;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Диспетчер, выбирающий фабрику браузера через SPI.
 */
public class SeleniumBrowserCapabilities {

    private final Map<String, BrowserDriverFactory> registry;
    private final Map<String, Object> seleniumConfig;

    public SeleniumBrowserCapabilities() {
        // Загружаем selenium_runtime.yaml
        this.seleniumConfig = RuntimeReader.getModuleConfig("selenium_runtime");

        // Регистрируем все фабрики через ServiceLoader
        registry = ServiceLoader.load(BrowserDriverFactory.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toMap(
                        f -> f.getBrowserName().toLowerCase(Locale.ROOT),
                        f -> f
                ));
    }

    public WebDriver createDriver() {
        String browser = Optional.ofNullable((String) seleniumConfig.get("browser"))
                .orElse("chrome")
                .trim()
                .toLowerCase(Locale.ROOT);

        BrowserDriverFactory factory = registry.get(browser);
        if (factory == null) {
            throw new IllegalStateException("No BrowserDriverFactory found for: " + browser);
        }
        return factory.createDriver(seleniumConfig);
    }
}
