package pages;

import actions.SeleniumActions;
import driver.DriverManager;

/**
 * Базовый класс для всех Selenium-страниц.
 * Предоставляет доступ к SeleniumActions.
 */
public abstract class BaseSeleniumPage {

    protected final SeleniumActions actions;

    protected BaseSeleniumPage() {
        this.actions = new SeleniumActions(DriverManager.getDriver());
    }
}