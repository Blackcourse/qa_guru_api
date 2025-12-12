package tests.reqressTests;
import models.lombok.UserDataResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.*;
import static specs.ResponseCodeSpec.*;

public class ReqresTestsWithModel extends TestBaseAPI {

   @Test
    @DisplayName("Проверка имени конкретного пользователя")
    void getListUsersTest () {
        UserDataResponseModel userData = step ("Запрос имени пользователя", () ->
        given(getUsersSpec)
                .when ()
                .get("/users")
                .then()
                .spec(responseSpecificationBuilder(200))
                .extract().as(UserDataResponseModel.class));

        step ("Проверка конкретного имени пользовтаеля", () ->
                assertEquals("Tracey", userData.getData().get(5).getFirstName()));

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
                    .spec(responseSpecificationBuilder(204));
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
                    .spec(responseSpecificationBuilder(400)).
                    body("error", equalTo("Empty request body"));

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
                .spec(responseSpecificationBuilder(401))
                .body("error", is ("api_key_required"));
        });
    }

    @Test
    @DisplayName("Запрос аватара у конретного пользователя")
    void getAvatarFromUser7 () {
        UserDataResponseModel getUserAvatar = step ("Запрос автара пользователя", () ->
        given(getUsersSpec)
                .when ()
                .get("/users")
                .then()
                .spec(responseSpecificationBuilder(200))
                .extract().as(UserDataResponseModel.class));

        step ("Проверка аватара пользователя", () ->
                assertEquals("https://reqres.in/img/faces/1-image.jpg", getUserAvatar.getData().get(0).getAvatar()));
    }











    }






