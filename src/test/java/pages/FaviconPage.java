package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.open;

public class FaviconPage {

    @Step
            ("Oткрытие страницы")
    public FaviconPage openPage () {
    open("/favicon.ico");
    return this;
    }
}
