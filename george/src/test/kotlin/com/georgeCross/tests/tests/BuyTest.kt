package com.georgeCross.tests.tests

import com.codeborne.selenide.Selenide.element
import com.georgeCross.com.georgeCross.tests.BaseUiTest
import com.georgeCross.tests.pages.BuyPage
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.openqa.selenium.By

class BuyTest : BaseUiTest() {

    private val buyPage = BuyPage()

    @Test
    @DisplayName("Отправка формы")
    fun submitForm() {
        headerEvaluationBtn.click()
        buyPage.nameInput.sendKeys("Иван Иванов")
        buyPage.numberInput.sendKeys("+79167777777")
        buyPage.descInput.sendKeys("Описание предмета")
        element(By.xpath("//input[@type='file']")).uploadFromClasspath("flag.jpg")
        buyPage.submitValuationButton.click()
    }
}