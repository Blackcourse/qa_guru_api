package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.UserDataModel;
import models.lombok.UserDataResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.LoginSpec;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.*;
import static specs.ResponseCodeSpec.*;

public class ReqresTestsWithModel extends TestBase {

    @Test
    @DisplayName("Проверка имени конкретного пользователя")
    void getListUsersTest () {
        UserDataModel firstname = step ("Запрос имени пользователя", () ->
        given(getUsersSpec)
                .when ()
                .get("/users")
                .then()
                .spec(responseSpec200)
                .extract().as(UserDataModel.class));

        step ("Проверка конкретного имени пользовтаеля", () ->
                assertEquals("Tracey", firstname.getData().get(5).getEmail()));

    }
    @Test
    @DisplayName("Удаление пользователя")
    void unsuccessfullDeleteUserTest() {
        step("Отправка запроса на удаление пользователя", () -> {
            given()
                    .spec(loginRequestSpec)
                    .when()
                    .delete("/users/4")
                    .then()
                    .spec(responseSpec204);
        });
    }

   @Test
    @DisplayName("Регистрация пользователя без тела запроса")
    void unsuccessRegistrationUserTest() {
        step("Отправка запроса на создание нового пользователя без тела", () -> {
            given(getUsersSpec)
                    .when()
                    .post("/users")
                    .then()
                    .spec(responseSpec400).body(equalTo("error":"Empty request body",
                    "message":"Request body cannot be empty for JSON endpoints"));

        });
    }

    @Test
    @DisplayName("Запрос о конкретном пользователе без хэдеров")
    void getUsersWithoutHeadersTest () {
        step("Отправка запроса о пользвоателе", () -> {
        given(loginRequestWithoutHeaders)
                .when()
                .get("/users/1")
                .then()
                .spec(responseSpec401)
                .body("error", is ("Missing API key"));
        });
    }

    @Test
    @DisplayName("Запрос аватара у конретного пользователя")
    void getAvatarFromUser7 () {
        given()
                .log().uri()
                .header("X-API-Key", "reqres-free-v1")
                .get("/users/7")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("data.avatar", is("https://reqres.in/img/faces/7-image.jpg"));
    }











    }






