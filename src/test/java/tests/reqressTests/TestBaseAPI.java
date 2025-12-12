package tests.reqressTests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;



public class TestBaseAPI {
        @BeforeAll
        public static void setupEnviroment() {
            RestAssured.baseURI = "https://reqres.in";
            RestAssured.basePath = "/api";
        }
    }

