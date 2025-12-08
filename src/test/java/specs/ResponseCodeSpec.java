package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class ResponseCodeSpec {

    public static ResponseSpecification loginResponseSpec= new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();


    public static ResponseSpecification responseSpecificationBuilder(int statusCode) {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .expectStatusCode(statusCode)
                .build();
    }




}
