package com.georgeCross.tests.pages

import com.codeborne.selenide.Selenide.*
import org.openqa.selenium.By

class ContactPage {

    val title= element(By.xpath("//h2[contains(text(), 'Контактная информация')]"))

    val contactBlocks = `$$`(By.xpath("//div[contains(@class, 'row g-4')]//div[contains(@class, 'col-md-4')]"))
}