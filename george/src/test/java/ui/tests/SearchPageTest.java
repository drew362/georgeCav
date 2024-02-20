package ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import ui.BaseTest;
import ui.pages.SearchPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Feature("Поиск")
public class SearchPageTest extends BaseTest {
    SearchPage searchPage = new SearchPage();

    //    @BeforeEach
    //    public void prepareForTest() {
    //        Selenide.open("http://localhost:8086/search");
    //    }

    @Test
    @Description("Отображение элементов страницы")
    public void allThingsOnPageIsVisible() {
        open("http://localhost:8086/search");
        searchPage.textField.shouldBe(Condition.visible);
        searchPage.searchButton.shouldBe(Condition.visible);
    }

    @Test
    @Description("Проверка поиска по номеру креста")
    public void checkRezultInFieldText() {
        open("http://localhost:8086/search");
        searchPage.searchButton.setValue("200151");
        searchPage.searchButton.click();
        searchPage.resultSearch.shouldHave(text("КЛОПКОВ Петр"));
        Selenide.sleep(2000);
    }
}
