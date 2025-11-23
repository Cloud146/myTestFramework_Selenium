package capabilities;

import driver.BrowserDriverFactory;
import logging.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация BrowserDriverFactory для Chrome.
 * Читает настройки из selenium_runtime.yaml.
 */
public class ChromeBrowserDriverFactory implements BrowserDriverFactory {

    private static final Logger log = Log.get(ChromeBrowserDriverFactory.class);

    @Override
    public String getBrowserName() {
        return "chrome";
    }

    @SuppressWarnings("unchecked")
    @Override
    public WebDriver createDriver(Map<String, Object> config) {
        ChromeOptions options = new ChromeOptions();

        // headless
        boolean headless = Boolean.parseBoolean(config.getOrDefault("headless", "false").toString());
        if (headless) {
            options.addArguments("--headless=new");
        }

        // args
        Object argsObj = config.get("args");
        if (argsObj instanceof List) {
            List<String> args = ((List<?>) argsObj).stream()
                    .map(Object::toString)
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toList());
            options.addArguments(args);
        }

        // pageLoadStrategy
        String strategy = Optional.ofNullable(config.get("pageLoadStrategy"))
                .map(Object::toString)
                .map(String::toLowerCase)
                .orElse("normal");

        switch (strategy) {
            case "eager":
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                break;
            case "none":
                options.setPageLoadStrategy(PageLoadStrategy.NONE);
                break;
            default:
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        }

        String driverPath = (String) config.get("driverPath");
        String driverVersion = (String) config.get("driverVersion");

        if (driverPath != null && !driverPath.isBlank()) {
            URL resource = getClass().getClassLoader().getResource(driverPath);
            if (resource == null) {
                throw new IllegalStateException("Не найден драйвер в ресурсах: " + driverPath);
            }
            String absolutePath = null;
            try {
                absolutePath = Paths.get(resource.toURI()).toFile().getAbsolutePath();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            System.out.println(absolutePath);
            System.setProperty("webdriver.chrome.driver", absolutePath);
        } else if (driverVersion != null && !driverVersion.isBlank()) {
            WebDriverManager.chromedriver().driverVersion(driverVersion).setup();
        } else {
            WebDriverManager.chromedriver().setup();
        }

        WebDriver driver = new ChromeDriver(options);

        applyWindowMode(driver, config);

        Capabilities caps = ((HasCapabilities) driver).getCapabilities();

        log.info("Выбранный браузер: {}", caps.getBrowserName());
        log.trace("Версия браузера: {}", caps.getBrowserVersion());
        log.trace("Версия вебдрайвера: {}", caps.getCapability("chrome").toString());
        return driver;
    }

    @SuppressWarnings("unchecked")
    private void applyWindowMode(WebDriver driver, Map<String, Object> config) {
        Map<String, Object> window = (Map<String, Object>) config.getOrDefault("window", Collections.emptyMap());
        String mode = Optional.ofNullable(window.get("mode"))
                .map(Object::toString)
                .map(String::toLowerCase)
                .orElse("");

        switch (mode) {
            case "maximize":
                driver.manage().window().maximize();
                break;
            case "fullscreen":
                driver.manage().window().fullscreen();
                break;
            default:
                if (mode.startsWith("size:")) {
                    Dimension size = parseSize(mode);
                    if (size != null) {
                        driver.manage().window().setSize(size);
                    }
                }
        }
    }

    private Dimension parseSize(String mode) {
        try {
            String payload = mode.substring("size:".length());
            String[] parts = payload.split("x");
            if (parts.length != 2) return null;
            int w = Integer.parseInt(parts[0].trim());
            int h = Integer.parseInt(parts[1].trim());
            return new Dimension(w, h);
        } catch (Exception e) {
            return null;
        }
    }
}
