package specs;

import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class LoginReqressInSpec {
    public static RequestSpecification loginRequestSpec= with ()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON)
            .header("x-api-key", "reqres_382e7023e98e493785b41b2d0ae12a06");

    public static RequestSpecification loginRequestWithoutHeaders= with ()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON);



    public static RequestSpecification getUsersSpec= with ()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON)
            .header("x-api-key", "reqres_382e7023e98e493785b41b2d0ae12a06");







}
