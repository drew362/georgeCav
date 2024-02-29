package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.After;
import org.junit.Before;

abstract public class BaseTest {
    public void setUp() {
        Configuration.browser = "chrome";
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver","/Users/a.kozin/Downloads/chromedriver");
//        Configuration.browserBinary="/Users/a.kozin/Downloads/chromedriver";
        Configuration.browserSize = "1920x1080";
        SelenideLogger.addListener("AllureSelenide",new AllureSelenide());
        Configuration.headless = false;
    }

    @Before
    public void init() {
        setUp();
    }

    @After
    public void close() {
        Selenide.closeWebDriver();
    }
}
