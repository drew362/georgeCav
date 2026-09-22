package com.georgeCross.tests

import com.georgeCross.com.georgeCross.tests.BaseUiTest
import com.georgeCross.tests.pages.SearchPage
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("Автотесты Aquilon UI")
@Feature("Тесты по поиску")

class SearchTest : BaseUiTest() {

    private val searchPage =  SearchPage()

    @Test
    @DisplayName("Проверка поиска Георгиевского креста по номеру")
    fun searhNumber(){
        searchPage.searchNumber()
    }
}