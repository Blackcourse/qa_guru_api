package tests.DemoqaTests;
import io.restassured.response.Response;
import models.AuthorizationModel;
import models.BookModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static specs.DemoQaSpec.baseSpec;
import static specs.DemoQaSpec.responseSpec;
import static tests.DemoqaTests.TestData.login;
import static tests.DemoqaTests.TestData.password;

@Tag("demoqa_tests")
public class CollectionTests extends TestBase {

        @Test

            @DisplayName("Очистка коллекции и добавление книги в коллекцию")
            void addBookToCollectionTest() {
                AuthorizationModel authData = new  AuthorizationModel();
                authData.setUserName(login);
                authData.setPassword(password);

                Response authResponse = step("Вход под логином юзера", ()->
                        given(baseSpec)
                                .body(authData)
                                .when()
                                .post("/Account/v1/Login")
                                .then()
                                .spec(responseSpec(200))
                                .extract().response());

                step("Очистка коллекции", ()->
                        given(baseSpec)
                                .header("Authorization", "Bearer " + authResponse.path("token"))
                                .queryParams("UserId", authResponse.path("userId"))
                                .when()
                                .delete("/BookStore/v1/Books")
                                .then()
                                .spec(responseSpec(204)));

             String isbn = "9781449365035";
                String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                        authResponse.path("userId"),isbn);

                step("Добавление книги в коллекцию", ()->
                        given(baseSpec)
                                .header("Authorization", "Bearer " + authResponse.path("token"))
                                .body(bookData)
                                .when()
                                .post("/BookStore/v1/Books")
                                .then()
                                .spec(responseSpec(201)));

                step("Открытие страницы", ()->
                        open("/favicon.ico"));

                step("Добавление куки", ()-> {
                    getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
                    getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
                    getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));
                });

                step("Отрытие профиля пользователя", ()->
                        open("/profile"));

                step("Проверка книги в коллекции", ()->
                        $(".ReactTable").shouldHave(text("Speaking JavaScript")));
            }

            @Test
            @DisplayName("Удаление книги из коллекции и добавление этой книги в коллекцию")
            void addBookToCollection_withDeleteOneBook_Test() {
                AuthorizationModel authData = new  AuthorizationModel();
                authData.setUserName(login);
                authData.setPassword(password);

                Response authResponse = step("Login by user", ()->
                        given(baseSpec)
                                .body(authData)
                                .when()
                                .post("/Account/v1/Login")
                                .then()
                                .spec(responseSpec(200))
                                .extract().response());

                BookModel deleteBookData = new BookModel();
                deleteBookData.setUserId(authResponse.path("userId"));
                deleteBookData.setIsbn("isbn");

                step("Удаление книги из коллекции", ()->
                        given(baseSpec)
                                .header("Authorization", "Bearer " + authResponse.path("token"))
                                .body(deleteBookData)
                                .when()
                                .delete("/BookStore/v1/Book")
                                .then()
                                .spec(responseSpec(204)));

                String isbn = "9781449365035";
                String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                        authResponse.path("userId") , isbn);

                step("Добавление книги в коллекцию", ()->
                        given(baseSpec)
                                .header("Authorization", "Bearer " + authResponse.path("token"))
                                .body(bookData)
                                .when()
                                .post("/BookStore/v1/Books")
                                .then()
                                .spec(responseSpec(201)));

                step("Открытие страницы", ()->
                        open("/favicon.ico"));

                step("Добавление куки", ()-> {
                    getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
                    getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
                    getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));
                });
                step("Открытие профиля", ()->
                        open("/profile"));

                step("Проверки книги в коллекции", ()->
                        $(".ReactTable").shouldHave(text("Speaking JavaScript")));
            }
        }




