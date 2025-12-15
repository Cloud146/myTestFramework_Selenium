package capabilities;

import browsers.BrowserConfig;
import config.RuntimeReader;
import driver.BrowserDriverFactory;
import org.openqa.selenium.WebDriver;
import java.util.*;
import java.util.stream.Collectors;

public class SeleniumBrowserCapabilities {

    private final Map<String, BrowserDriverFactory> registry;

    public SeleniumBrowserCapabilities() {
        registry = ServiceLoader.load(BrowserDriverFactory.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toMap(
                        f -> f.getBrowserName().toLowerCase(Locale.ROOT),
                        f -> f
                ));
    }

    public WebDriver createDriver() {
        String browserName = Optional.ofNullable(RuntimeReader.getString("browser"))
                .orElse("chrome")
                .trim()
                .toLowerCase(Locale.ROOT);

        BrowserDriverFactory factory = registry.get(browserName);
        if (factory == null) {
            throw new IllegalStateException("Для браузера '" + browserName + "' не найдена реализация (фабрика) в Selenium модуле.");
        }

        BrowserConfig config = RuntimeReader.getBrowserConfig(browserName);

        return factory.createDriver(config);
    }
}
