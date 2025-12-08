package tests.DemoqaTests;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import io.qameta.allure.selenide.AllureSelenide;
import specs.DemoQaSpec;

import static com.codeborne.selenide.Selenide.closeWebDriver;


public class TestBase {
    @BeforeAll
    public static void setupEnviroment() {
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl="https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.remote = System.getProperty("remote", "https://user1:1234@selenoid.autotests.cloud/wd/hub");
    }

    @BeforeEach
    void allureListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }


    @AfterEach
    void attachAndShutDown () {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

        closeWebDriver();

    }

}
