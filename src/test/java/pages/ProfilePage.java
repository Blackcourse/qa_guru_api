package pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static tests.demoqaTests.TestData.login;

public class ProfilePage {


    @Step("Открытие страницы профиля")
    public ProfilePage openProfilePage() {
        open("/profile");
        return this;
    }

    @Step("Проверка имени в профиле")
    public ProfilePage checkUserName(){
        $("#userName-value").shouldHave(text(login));
        return this;
    }

    @Step("Проверка наличии книги в коллекции")
    public ProfilePage checkBookInCollection(){
        $(".ReactTable").shouldHave(text("Speaking JavaScript"));
        return this;
    }

    @Step("Удаление книги из коллекции")
    public ProfilePage deleteOneBookInCollection(){
        $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();
        return this;
    }

    @Step("Проверка, что коллекция пустая")
    public ProfilePage checkCollectionIsEmpty(){
        $(".rt-noData").shouldHave(text("No rows found"));
        return this;
    }
}
