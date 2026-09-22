package com.georgeCross.tests.tests

import com.codeborne.selenide.CollectionCondition
import com.georgeCross.com.georgeCross.tests.BaseUiTest
import com.georgeCross.tests.pages.ShopPage
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("Автотесты Aquilon UI")
@Feature("Тесты по магазину")

class ShopTest : BaseUiTest() {

    private val shopPage = ShopPage()

    @Test
    @DisplayName("Проверка категорий")
    fun openCategory() {
        headerShopBtn.click()
        val count = shopPage.categoryButtons.size()
        for (i in 0 until count) {
            shopPage.categoryButtons[i].click()
        }
    }

    @Test
    @DisplayName("Проверка количества всех товаров ")
    fun openCategoryProduct() {
        headerShopBtn.click()
        shopPage.title0.exists()
        shopPage.products.shouldHave(CollectionCondition.size(37))
    }

    @Test
    @DisplayName("Проверка количества товаров категорий1")
    fun openCategoryProduct1() {
        headerShopBtn.click()
        shopPage.categoryButtons[1].click()
        shopPage.title1.exists()
        shopPage.products.shouldHave(CollectionCondition.size(17))
    }

    @Test
    @DisplayName("Проверка количества товаров категорий2")
    fun openCategoryProduct2() {
        headerShopBtn.click()
        shopPage.categoryButtons[2].click()
        shopPage.title2.exists()
        shopPage.products.shouldHave(CollectionCondition.size(9))
    }

    @Test
    @DisplayName("Проверка количества товаров категорий3")
    fun openCategoryProduct3() {
        headerShopBtn.click()
        shopPage.categoryButtons[3].click()
        shopPage.title3.exists()
        shopPage.products.shouldHave(CollectionCondition.size(4))
    }

    @Test
    @DisplayName("Проверка количества товаров категорий4")
    fun openCategoryProduct4() {
        headerShopBtn.click()
        shopPage.categoryButtons[3].click()
        shopPage.title4.exists()
        shopPage.products.shouldHave(CollectionCondition.size(4))
    }

    @Test
    @DisplayName("Проверка количества товаров категорий4")
    fun openCategoryProduct5() {
        headerShopBtn.click()
        shopPage.categoryButtons[5].click()
        shopPage.title5.exists()
        shopPage.products.shouldHave(CollectionCondition.size(3))
    }
}