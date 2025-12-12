package tests.reqressTests;

import models.LoginBodyModel;
import models.LoginResponseModel;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.loginRequestSpec;

public class ReqresInExtendsTests extends TestBaseAPI {

    @Test
    void successfulLoginPojoTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .header("X-API-Key", "reqres_382e7023e98e493785b41b2d0ae12a06")

                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().body().as(LoginResponseModel.class);

        assertEquals ("QpwL5tke4Pnpja7X4", response.getToken());


    }
    @Test
    void successfulLoginLombokTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .header("X-API-Key", "reqres_382e7023e98e493785b41b2d0ae12a06")

                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().body().as(LoginResponseLombokModel.class);

        assertEquals ("QpwL5tke4Pnpja7X4", response.getToken());


    }

    @Test
    void successfulLoginCustomAllureTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .header("X-API-Key", "reqres_382e7023e98e493785b41b2d0ae12a06")

                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().body().as(LoginResponseLombokModel.class);

        assertEquals ("QpwL5tke4Pnpja7X4", response.getToken());


    }

    @Test
    void successfulLoginWithStepsTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = step ("Make request", ()->
            given ()
            .filter(withCustomTemplates())
                    .log().uri()
                    .log().body()
                    .log().headers()
                    .body(authData)
                    .contentType(JSON)
                    .header("X-API-Key", "reqres-free-v1")

                    .when()
                    .post("/login")

                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().body().as(LoginResponseLombokModel.class));

            step ("Check response", ()-> {
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
            });

    }


    @Test
    void successfulLoginWithSpecsTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make request", () ->
                given(loginRequestSpec)
                        .body(authData)

                        .when()
                        .post("/login")

                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().body().as(LoginResponseLombokModel.class));

        step("Check response", () -> {
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });

    }

    @Test
    void getListUsersTest () {
        given()
                .log().uri()
                .header("X-API-Key", "reqres-free-v1")
                .get("/users")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("data[5].first_name", equalTo("Tracey"));

    }
    @Test
    void getUsersWithoutHeadersTest () {
        given()
                .log().uri()
                .get("/users")
                .then()
                .log().body()
                .log().status()
                .statusCode(401)
                .body("error", is ("Missing API key"));
    }


    @Test
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


    @Test
    void unsuccessRegistrationUserTest () {
        given()
                //  .body(uncorrectUserData)
                .log().uri()
                .post("/users")
                .then()
                .log().body()
                .log().status()
                .statusCode(415)
                .body("error", is ("unsupported_charset"));
    }

    @Test
    void unsuccessfullDeleteUserTest () {
        given()
                .log().uri()
                .header("X-API-Key", "reqres-free-v1")
                .delete("/users/2")
                .then()
                .log().body()
                .log().status()
                .statusCode(204);

    }




}

