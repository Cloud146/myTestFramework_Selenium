package capabilities;

import browsers.BrowserConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;

public class FirefoxBrowserDriverFactory extends AbstractBrowserDriverFactory {

    private static final Logger log = Log.get(FirefoxBrowserDriverFactory.class);

    @Override
    public String getBrowserName() {
        return "firefox";
    }

    @Override
    public WebDriver createDriver(BrowserConfig config) {
        setupDriverBinary(config, "webdriver.gecko.driver", WebDriverManager.firefoxdriver());

        FirefoxOptions options = new FirefoxOptions();

        if (config.getBinaryPath() != null && !config.getBinaryPath().isBlank()) {
            log.info("[BrowserFactory] Используется кастомный путь к браузеру Firefox: {}", config.getBinaryPath());
            options.setBinary(config.getBinaryPath());
        }

        if (config.isHeadless()) {
            options.addArguments("-headless");
        }

        if (!config.getArgs().isEmpty()) {
            options.addArguments(config.getArgs());
        }

        for (String pref : config.getPreferences()) {
            applyPreference(options, pref);
        }

        options.setPageLoadStrategy(getPageLoadStrategy(config));

        WebDriver driver = new FirefoxDriver(options);
        applyWindowSettings(driver, config);
        logBrowserInformation(driver);

        return driver;
    }

    private void applyPreference(FirefoxOptions options, String prefString) {
        if (prefString == null || prefString.isBlank()) return;

        String[] parts = prefString.split("=", 2);

        if (parts.length != 2) {
            log.warn("Пропущена настройка Firefox: '{}'. Ожидается формат 'ключ=значение'", prefString);
            return;
        }

        String key = parts[0].trim();
        String valueStr = parts[1].trim();

        Object typedValue = parseValue(valueStr);

        log.debug("Настройка Firefox: {} = {} (Тип: {})", key, typedValue, typedValue.getClass().getSimpleName());

        if (typedValue instanceof Boolean) {
            options.addPreference(key, (Boolean) typedValue);
        } else if (typedValue instanceof Integer) {
            options.addPreference(key, (Integer) typedValue);
        } else {
            options.addPreference(key, (String) typedValue);
        }
    }

    private Object parseValue(String value) {
        if ("true".equalsIgnoreCase(value)) return true;
        if ("false".equalsIgnoreCase(value)) return false;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return value;
        }
    }
}
