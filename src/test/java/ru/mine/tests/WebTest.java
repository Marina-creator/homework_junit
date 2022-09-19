package ru.mine.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.apache.commons.codec.language.bm.Lang;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ru.mine.pages.WebTestPages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

import java.util.List;
import java.util.stream.Stream;

public class WebTest {

    WebTestPages webTestPages = new WebTestPages();

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
//        Configuration.holdBrowserOpen = true;
    }

    @ValueSource(strings = {"0373100103922000103",
            "Поставка оборудования автоматической обработки данных", "7704097560"})
    @ParameterizedTest(name = "Поиск на входящее значение {0}")
    void searchFieldValidation(String testData1) {
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
    void searchFieldValidationLong(String testData, String expectedResult) {
        webTestPages.openPage();
        $(".search-box__input").setValue(testData);
        $(".button__icon").click();
        $("#auction_search_results")
                .shouldHave(text(expectedResult));
    }

    static Stream<Arguments> dataForroseltorgMenuLang() {
        return Stream.of(
                Arguments.of("Ru", List.of("Торги", "Услуги и сервисы", "Клиентам", "О площадке",
                "Помощь")),
                Arguments.of("En", List.of("Bargaining", "Services and Services", "Clients",
                        "About the site", "Help"))
        );
    }
    @MethodSource("dataForroseltorgMenuLang")
    @ParameterizedTest(name = "Для языка \"{0}\" отображаются название кнопок \"{1}\"")
    void roseltorgMenuLang(String lang, List<String> expectedButtons) {
        open("https://www.roseltorg.ru/");
        $(".lang-selector__current-value").click();
        $$(".lang-selector__options li a").find(text(lang)).click();
        $$(".menu__list li span").shouldHave(CollectionCondition.texts(expectedButtons));
    }


}
