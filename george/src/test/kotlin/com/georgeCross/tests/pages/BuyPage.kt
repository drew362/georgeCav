package com.georgeCross.tests.pages

import com.codeborne.selenide.Selenide.*
import org.openqa.selenium.By

class BuyPage {

    val nameInput = element(By.xpath("//input[contains(@placeholder, 'обращаться')]"))
    val numberInput = element(By.xpath("//input[contains(@placeholder, '+7')]"))
    val descInput = element(By.xpath("//textarea[contains(@placeholder, 'Укажите')]"))
    val submitValuationButton = `$x`("//button[@type='submit']")

}