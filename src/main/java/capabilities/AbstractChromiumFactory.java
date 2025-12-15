package capabilities;

import browsers.BrowserConfig;
import logging.Log;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.slf4j.Logger;

public abstract class AbstractChromiumFactory extends AbstractBrowserDriverFactory {

    private static final Logger log = Log.get(AbstractChromiumFactory.class);

    protected void configureCommonOptions(ChromiumOptions<?> options, BrowserConfig config) {
        if (config.getBinaryPath() != null && !config.getBinaryPath().isBlank()) {
            log.info("[ChromiumFactory] Используется кастомный путь к браузеру: {}", config.getBinaryPath());
            options.setBinary(config.getBinaryPath());
        }

        if (config.isHeadless()) {
            options.addArguments("--headless=new");
        }

        if (!config.getArgs().isEmpty()) {
            options.addArguments(config.getArgs());
        }

        options.setPageLoadStrategy(getPageLoadStrategy(config));
    }
}
