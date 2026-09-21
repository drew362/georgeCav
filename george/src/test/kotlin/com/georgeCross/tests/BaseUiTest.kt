package com.georgeCross.com.georgeCross.tests

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.`$`
import org.junit.jupiter.api.BeforeAll
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeOptions

abstract class BaseUiTest {

    val headerShopBtn         = `$`(By.xpath("//a[contains(text(), 'Магазин')]"))
    val headerEvaluationBtn   = `$`(By.xpath("//a[contains(text(), 'Скупка и Оценка')]"))
    val headerContactsBtn     = `$`(By.xpath("//a[contains(text(), 'Контакты')]"))

    companion object {

        @JvmStatic
        @BeforeAll
        fun setUp() {

            val options = ChromeOptions().apply {
                addArguments("--no-sandbox")
                addArguments("--disable-dev-shm-usage")
                addArguments("--disable-gpu")
            }

            Configuration.baseUrl = "https://aquilon-antique.ru/"
            Configuration.browser = "chrome"
            Configuration.headless = true
            Configuration.screenshots = true
            Configuration.timeout = 20000

            Configuration.browserCapabilities = options
        }
    }

    @BeforeEach
    fun openUrl(){
        open("")
    }
}
