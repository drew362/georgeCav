package com.georgeCross.tests.pages

import com.codeborne.selenide.Selenide.*
import org.openqa.selenium.By

class ShopPage {

    val title0 = element(By.xpath("//h2[contains(text(), 'Все товары')]"))
    val title1 = element(By.xpath("//h2[contains(text(), 'Допетровские монеты')]"))
    val title2 = element(By.xpath("//h2[contains(text(), 'Монеты Николая II')]"))
    val title3 = element(By.xpath("//h2[contains(text(), 'Антикварное оружие')]"))
    val title4 = element(By.xpath("//h2[contains(text(), 'Восток')]"))
    val title5 = element(By.xpath("//h2[contains(text(), 'Медали Николая II')]"))


    val categoryButtons = elements(By.xpath("//div[contains(@class, 'sticky-md-top')]//button[contains(@class, 'list-group-item-action')]"))
    val products = elements(By.xpath("//div[contains(@class, 'row-cols-lg-3')]//a"))
}
