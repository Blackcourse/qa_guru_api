import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.Is.is;


public class StatusTests {

    private static final Logger log = LoggerFactory.getLogger(StatusTests.class);

    @Test
    void checkTotalWithResponeLogs() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .body("total", is(5));
    }

    @Test
    void checkTotalWithStatusLogs() {
        given()
                .log().uri()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .log().all()
                .body("browsers.chrome", hasKey("127.0"))
                .body("browsers.firefox", hasKey("124.0"));
    }

}

