import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class LoginTests {
    String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

    @Test
    void successfulLoginTest() {

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .header("X-API-Key", "reqres-free-v1")
                .log().uri()
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .log().status()
                .statusCode(200);


    }
    @Test
    void unsuccessfulLogin415Test() {
        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .header("X-A-Key", "reqres-free-v1")
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .log().status()
                .statusCode(415);


    }


}
