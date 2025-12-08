package tests.DemoqaTests;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static tests.DemoqaTests.TestData.login;
import static tests.DemoqaTests.TestData.password;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


public class LoginTests extends TestBase {
    @Test
    void successfulLoginUITest() {
        open("/login");
        $("#userName").setValue(login);
        $("#password").setValue(password).pressEnter();
    }

        @Test
        void successfulLoginAPITest () {
            String authData = "{\"userName\":\"test123456\", \"password\":\"Test123456@\"}";

            Response authResponse = given()
                    .log().uri()
                    .log().method()
                    .log().body()
                    .contentType(JSON)
                    .body(authData)

                    .when()
                    .post("account/v1/login")

                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().response();
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
            getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

            open("/profile");


        }
    }

