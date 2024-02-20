package ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Feature;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ui.BaseTest;
import ui.pages.SearchPage;

import static com.codeborne.selenide.Condition.text;

@Feature("Поиск")
public class SearchPageTest extends BaseTest {

    SearchPage searchPage = new SearchPage();

    @Test
    @DisplayName("Отображение элементов страницы")
    public void allThingsOnPageIsVisible() {
        Selenide.open("http://localhost:8086/search");
        searchPage.textField.shouldBe(Condition.visible);
        searchPage.searchButton.shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Проверка поиска по номеру креста")
    public void checkRezultInFieldText() {
        Selenide.open("http://localhost:8086/search");
        searchPage.textField.setValue("200151");
        searchPage.searchButton.click();
        searchPage.resultSearch.shouldHave(text("КЛОПКОВ Петр"));
        Selenide.sleep(2000);
    }
}