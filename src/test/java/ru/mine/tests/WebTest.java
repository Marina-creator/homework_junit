package ru.mine.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.mine.pages.WebTestPages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    WebTestPages webTestPages = new WebTestPages();

    @BeforeEach
    static void setUp() {
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
//        Configuration.holdBrowserOpen = true;
    }

    @ValueSource(strings = {"0373100103922000103",
            "Поставка оборудования автоматической обработки данных", "7704097560"})
    @ParameterizedTest(name = "Поиск на входящее значение {0}")
    void searchFieldValidation (String testData1) {
        webTestPages.openPage();
        $(".search-box__input").setValue(testData1);
        $(".button__icon").click();
        $("#auction_search_results")
        .shouldHave(text("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ \"РОССИЙСКАЯ ГОСУДАРСТВЕННАЯ БИБЛИОТЕКА\""));
    }

     @CsvSource(value = {
         "0373100103922000103, Поставка оборудования автоматической обработки данных",
         "Поставка оборудования автоматической обработки данных, 0373100103922000103",
         "7704097560, ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ \"РОССИЙСКАЯ ГОСУДАРСТВЕННАЯ БИБЛИОТЕКА\""
    })
    @ParameterizedTest(name = "Поиск на входящее значение: \"{0}\" с результатом: \"{1}\"")
    void searchFieldValidationLong (String testData, String expectedResult) {
        webTestPages.openPage();
        $(".search-box__input").setValue(testData);
        $(".button__icon").click();
        $("#auction_search_results")
        .shouldHave(text(expectedResult));
    }




}
