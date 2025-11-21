import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class ReqresInTests {
    private static final Logger log = LoggerFactory.getLogger(ReqresInTests.class);

    String uncorrectUserData=  "{\"email\": \"sdf.ddd@regres.in\", \"password\": \"dsfdsficka\"}";

    @Test
    void getListUsersTest () {
        given()
                .log().uri()
                .header("X-API-Key", "reqres-free-v1")
                .get("https://reqres.in/api/users")
                .then()
                .log().body()
                .log().status()
                .statusCode(200);

    }
    @Test
    void getUsersWithoutHeadersTest () {
        given()
                .log().uri()
                .get("https://reqres.in/api/users")
                .then()
                .log().body()
                .log().status()
                .statusCode(401);
    }


    @Test
    void getRandomUserTest () {
        given()
                .log().uri()
                .header("X-API-Key", "reqres-free-v1")
                .get("https://reqres.in/api/users/255")
                .then()
                .log().body()
                .log().status()
                .statusCode(404);
    }


    @Test
    void unsuccessfulLRegistrationUserTest () {
        given()
                .body(uncorrectUserData)
                .log().uri()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .log().status()
                .statusCode(415);
    }

    @Test
    void unsuccessfullDeleteUserTest () {
        given()
                .log().uri()
                .header("X-API-Key", "reqres-free-v1")
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().body()
                .log().status()
                .statusCode(204);
    }




}
