package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class DemoQaSpec {
    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .expectStatusCode(statusCode)
                .build();
    }

    public static RequestSpecification baseSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(ContentType.JSON);




}

