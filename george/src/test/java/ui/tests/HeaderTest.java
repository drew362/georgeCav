package ui.tests;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Feature;
import org.junit.Assert;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import ui.BaseTest;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@Feature("Хэдэр")
public class HeaderTest extends BaseTest {

//    @Test
    @DisplayName("Проверка элемнтов Header")
    public void checkElementsHeaderPage() {
        Selenide.open("http://localhost:8086/search");
        $(By.xpath("//span[contains(@class,'fs-4')]")).shouldHave(text("Борей"))
                .shouldBe(Condition.visible);
        $(By.xpath("//a[contains(@href,'/search')]")).shouldHave(text("Поиск"))
                .shouldBe(Condition.visible);
        $(By.xpath("//a[contains(@href,'/shop')]")).shouldHave(text("Магазин"))
                .shouldBe(Condition.visible);
    }

//    @Test
    @DisplayName("Поверка ссылок Header")
    public void clickHeaderElements() {
        Selenide.open("http://localhost:8086/search");
        String url = WebDriverRunner.url();
        Assert.assertEquals(url, "http://localhost:8086/search");
        $(By.xpath("//a[contains(@href,'/shop')]")).click();
        Assert.assertEquals("//a[contains(@href,'/shop')]", "//a[contains(@href,'/shop')]");
        $(By.xpath("//span[contains(@class,'fs-4')]")).click();
        Assert.assertEquals("/", "/");
    }
}
