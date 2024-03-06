package uiSelenium.testSelenium;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import uiSelenium.BaseSeleniumTest;

public class SearchTest extends BaseSeleniumTest {

//    @Test
    public void checkSearchTest() {
        driver.get("http://localhost:8086/search");
    }
}
