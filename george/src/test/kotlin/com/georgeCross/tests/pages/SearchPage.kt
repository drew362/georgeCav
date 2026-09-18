package com.georgeCross.tests.pages

import com.codeborne.selenide.Selenide.*

class SearchPage {

    private val inputNumber = `$x`("//input")
    private val searchButton = `$x`("//button[contains(text(), 'Искать')]")
    private val badge = `$x`("//span[contains(., '231440')]")

    fun searchNumber(){
        inputNumber.click()
        inputNumber.sendKeys("231440")
        searchButton.click()
        badge.isDisplayed
    }
}