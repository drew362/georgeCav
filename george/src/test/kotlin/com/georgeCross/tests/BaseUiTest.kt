package com.georgeCross.com.georgeCross.tests

import com.codeborne.selenide.Configuration
import org.junit.jupiter.api.BeforeAll
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.chrome.ChromeOptions

abstract class BaseUiTest {

    companion object {

        @JvmStatic
        @BeforeAll
        fun setUp() {
//            System.setProperty("webdriver.chrome.driver", "C:\\Users\\drew\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe")
            Configuration.baseUrl = "https://aquilon-antique.ru/"
            Configuration.browser = "chrome"
            Configuration.headless = true
            Configuration.screenshots = true
            Configuration.timeout = 20000

            val options = ChromeOptions().apply {
                addArguments("--no-sandbox")
                addArguments("--disable-dev-shm-usage")
                addArguments("--disable-gpu")
                addArguments("--headless=new")
            }

            Configuration.browserCapabilities = options
        }
    }

    @BeforeEach
    fun openUrl(){
        open("")
    }
}