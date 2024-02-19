package ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.Test;
import org.openqa.selenium.By;
import ui.BaseTest;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@Feature("Поиск")
public class SearchPageTest extends BaseTest {

    @Test
    @Description("Отображение элементов страницы")
    public void allThingsOnPageIsVisible() {
        Selenide.open("http://localhost:8086/search");
        $(By.xpath("//input")).shouldBe(Condition.visible);
        $(By.xpath("//button")).shouldBe(Condition.visible);
    }

    @Test
    @Description("Проверка поиска по номеру креста")
    public void checkRezultInFieldText() {
        Selenide.open("http://localhost:8086/search");
        $(By.xpath("//input")).setValue("200151");
        $(By.xpath("//button")).click();
        $(By.xpath("//div[contains(@class,'alert')]")).shouldHave(text("КЛОПКОВ Петр"));
        Selenide.sleep(2000);
    }
}
