package ru.mine.pages;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

public class WebTestPages {

    public WebTestPages openPage(){
        open("https://www.roseltorg.ru/");
        executeJavaScript("document.querySelector(\".flocktory-widget-overlay\").remove();");
        return this;
    }
}
