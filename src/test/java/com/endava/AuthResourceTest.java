package com.endava;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class AuthResourceTest {

    @Test
    void loginValidCredentials() {
        given()
                .body("{\"name\":\"admin\",\"password\":\"quarkus\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body(not(empty()));
    }

    @Test
    void loginInvalidCredentials() {
        given()
                .body("{\"name\":\"admin\",\"password\":\"wrongpass\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/auth/login")
                .then()
                .statusCode(401);
    }

}
