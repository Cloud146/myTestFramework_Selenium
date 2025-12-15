package capabilities;

import browsers.BaseBrowserFactory;
import browsers.BrowserConfig;
import driver.BrowserDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;

public abstract class AbstractBrowserDriverFactory extends BaseBrowserFactory<WebDriver> implements BrowserDriverFactory {

    private static final String DRIVERS_FOLDER = "configuration_files/browsers_drivers/";

    protected void setupDriverBinary(BrowserConfig config, String systemPropertyKey, WebDriverManager wdm) {
        String driverFileName = config.getDriverPath();
        String driverVersion = config.getDriverVersion();

        if (driverFileName != null && !driverFileName.isBlank()) {

            String absolutePath = getAbsolutePathFromResources(DRIVERS_FOLDER, driverFileName);

            if (absolutePath != null) {
                System.setProperty(systemPropertyKey, absolutePath);
                log.info("[BrowserFactory] Используется локальный драйвер из ресурсов: {}", absolutePath);
                return;
            } else {
                log.warn("[BrowserFactory] Локальный драйвер '{}' не найден в папке '{}'. Используется WebDriverManager",
                        driverFileName, DRIVERS_FOLDER);
            }
        }

        if (driverVersion != null && !driverVersion.isBlank()) {
            log.debug("[BrowserFactory] Загрузка драйвера версии '{}' через WebDriverManager", driverVersion);
            wdm.driverVersion(driverVersion).setup();
        } else {
            log.debug("[BrowserFactory] Автоматическая загрузка последней версии драйвера через WebDriverManager");
            wdm.setup();
        }
    }

    protected void applyWindowSettings(WebDriver driver, BrowserConfig config) {
        String mode = config.getWindow().getMode().toLowerCase().trim();

        switch (mode) {
            case "maximize":
                driver.manage().window().maximize();
                break;
            case "fullscreen":
                driver.manage().window().fullscreen();
                break;
            default:
                int[] size = parseWindowSize(mode);
                if (size != null) {
                    driver.manage().window().setSize(new Dimension(size[0], size[1]));
                }
                break;
        }
    }

    protected PageLoadStrategy getPageLoadStrategy(BrowserConfig config) {
        String strategy = config.getPageLoadStrategy().toLowerCase().trim();
        switch (strategy) {
            case "eager": return PageLoadStrategy.EAGER;
            case "none": return PageLoadStrategy.NONE;
            default: return PageLoadStrategy.NORMAL;
        }
    }

    protected void logBrowserInformation(WebDriver driver) {
        try {
            Capabilities caps = ((HasCapabilities) driver).getCapabilities();
            String browserName = caps.getBrowserName().toUpperCase();
            String browserVersion = caps.getBrowserVersion();

            log.info("[BrowserFactory] Версия браузера {}: {}", browserName, browserVersion);
        } catch (Exception e) {
            log.warn("[BrowserFactory] Не удалось определить версию запущенного браузера");
        }
    }
}
