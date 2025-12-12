package tests.demoqaTests;
import io.restassured.response.Response;
import models.BookDataModel;
import models.AuthorizationModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import pages.FaviconPage;
import pages.ProfilePage;
import java.util.Collections;
import static tests.demoqaTests.TestData.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.DemoQaSpec.baseSpec;
import static specs.DemoQaSpec.responseSpec;



@Tag("demoqa_tests")
public class AddAndDeleteBook extends TestBase {

   FaviconPage faviconPage=new FaviconPage();
   ProfilePage profilePage=new ProfilePage();


    @Test
    @DisplayName("Добавление и удаление книги в коллекции пользователя")
    void addAndDeleteBookToCollectionTest() {
        AuthorizationModel authData = new  AuthorizationModel();
        authData.setUserName(login);
        authData.setPassword(password);

        Response authResponse = step("Вход под логином", ()->
                given(baseSpec)
                        .body(authData)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(responseSpec(200))
                        .extract().response());

        step("Удаление книги из коллекции", ()->
                given(baseSpec)
                        .header("Authorization", "Bearer " + authResponse.path("token"))
                        .queryParams("UserId", authResponse.path("userId"))
                        .when()
                        .delete("/BookStore/v1/Books")
                        .then()
                        .spec(responseSpec(204)));

        BookDataModel bookData = new BookDataModel();
        BookDataModel.CollectionOfIsbns isbnCollection = new BookDataModel.CollectionOfIsbns();
        bookData.setUserId(authResponse.path("userId"));
        isbnCollection.setIsbn(isbn);
        bookData.setCollectionOfIsbns(Collections.singletonList(isbnCollection));

        step("Добавление книги в коллекцию", ()->
                given(baseSpec)
                        .header("Authorization", "Bearer " + authResponse.path("token"))
                        .body(bookData)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(responseSpec (201)));

        faviconPage.openPage();

        step("Добавление куки", () -> {

        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));
    });

 profilePage.openProfilePage()
         .checkUserName()
                .checkBookInCollection()
                .deleteOneBookInCollection()
                .checkCollectionIsEmpty();


    }
}




