package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.BaseTest;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SearchPage extends BaseTest {
    public final SelenideElement linkSearch = $x("//a[contains(@href,'/search')]");
    public final SelenideElement textField = $x("//input");
    public SelenideElement searchButton = $x("//button");
    public final SelenideElement resultSearch = $(By.xpath("//div[contains(@class,'alert')]"));

    @FindBy(xpath = "//input")
    private WebElement textFieldInput;

    public void searchFor(String text) {
        textField.setValue(text).pressEnter();
    }

    public void openWebsite(String url) {
        Selenide.open(url);
    }

    public void clickLinkSearch() {
        linkSearch.click();
    }
}
