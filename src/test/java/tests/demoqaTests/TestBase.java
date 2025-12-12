package tests.demoqaTests;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;


public class TestBase {

    @BeforeAll
    static void setup(){
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI="https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        Configuration.browserVersion ="128.0";
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = System.getProperty("remote");


        // Настраиваем capabilities для Selenoid
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void allureListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }


    @AfterEach
    void addAttachmentsAndShutDown(){
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

        closeWebDriver();
    }

}
