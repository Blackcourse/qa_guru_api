package tests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ReqresInTests extends TestBase {

    String uncorrectUserData=  "{\"email\": \"sdf.ddd@regres.in\", \"password\": \"dsfdsficka\"}";

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
                .body(uncorrectUserData)
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
