package com.georgeCross.tests.tests

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.Condition.visible
import com.georgeCross.com.georgeCross.tests.BaseUiTest
import com.georgeCross.tests.pages.ContactPage
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ContactTest : BaseUiTest() {

    private val contactPage = ContactPage()

    @Test
    @DisplayName("Проверка контактов")
    fun submitForm() {
        headerContactsBtn.click()
        contactPage.title.shouldBe(visible)
        contactPage.contactBlocks.shouldHave(
            CollectionCondition.texts("Адрес офиса:", "Телефон:", "Электронная почта:")
        )
        contactPage.contactBlocks.first().shouldBe(visible)
    }
}