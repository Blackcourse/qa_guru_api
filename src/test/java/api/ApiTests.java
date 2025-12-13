package api;
import io.restassured.response.Response;
import models.AuthorizationModel;
import models.BookDataModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.Cookie;
import pages.FaviconPage;
import pages.ProfilePage;
import java.util.Collections;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.DemoQaSpec.baseSpec;
import static specs.DemoQaSpec.responseSpec;
import static tests.demoqaTests.TestData.*;

public class ApiTests {
    private Response authResponse ;
    FaviconPage faviconPage=new FaviconPage();



    @DisplayName("Вход по логином пользователя")
    public void login() {
        AuthorizationModel authData = new AuthorizationModel();
        authData.setUserName(login);
        authData.setPassword(password);

        authResponse = given(baseSpec)
                        .body(authData)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(responseSpec(200))
                        .extract().response();

    }

    @DisplayName("Удаление книги из коллекции" )
    public   void deleteBook () {
        given(baseSpec)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .queryParams("UserId", authResponse.path("userId"))
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(204));
    };

    @DisplayName("Добавление книги в коллекцию")
    public void addBookToCollection () {
        BookDataModel bookData = new BookDataModel();
        BookDataModel.CollectionOfIsbns isbnCollection = new BookDataModel.CollectionOfIsbns();
        bookData.setUserId(authResponse.path("userId"));
        isbnCollection.setIsbn(isbn);
        bookData.setCollectionOfIsbns(Collections.singletonList(isbnCollection));

        given(baseSpec)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec (201));

        faviconPage.openPage();

    }

    @DisplayName("Добавление куки")
    public void addCookie () {

        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));
    }



}
