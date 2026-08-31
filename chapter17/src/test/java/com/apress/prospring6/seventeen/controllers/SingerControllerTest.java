package com.apress.prospring6.seventeen.controllers;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Requires the WAR deployed to an external Tomcat at context path /ch17 - see chapter17/docker-compose.yml for the DB")
public class SingerControllerTest {

    @BeforeEach
    void setUp() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void johnShouldNotSeeTheDeleteButton() {
        var cfg = new FormAuthConfig("/ch17/auth", "user", "pass")
                .withLoggingEnabled();

        String responseStr = given()
                .contentType(ContentType.URLENC)
                .auth().form("john", "doe", cfg)
                .when().get("/ch17/singer/1")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().asString();

        assertAll(
                () -> assertTrue(responseStr.contains("<div class=\"card-header\">Singer Details</div>")),
                () -> assertTrue(responseStr.contains("<td>Mayer</td>")),
                () -> assertFalse(responseStr.contains("Delete"))
        );
    }

    @Test
    void johnShouldNotBeAllowedToDeleteString() {
        var cfg = new FormAuthConfig("/ch17/auth", "user", "pass")
                .withLoggingEnabled();

        String responseStr = given()
                .contentType(ContentType.URLENC)
                .auth().form("john", "doe", cfg)
                .when().delete("/ch17/singer/1")
                .then()
                .assertThat().statusCode(HttpStatus.FORBIDDEN.value())
                .extract().body().asString();
    }

    @Test
    void adminShouldSeeTheDeleteButton() {
        var cfg = new FormAuthConfig("/ch17/auth", "user", "pass")
                .withLoggingEnabled();

        String responseStr = given()
                .contentType(ContentType.URLENC)
                .auth().form("admin", "admin", cfg)
                .when().get("/ch17/singer/1")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().asString();

        assertAll(
                () -> assertTrue(responseStr.contains("<div class=\"card-header\">Singer Details</div>")),
                () -> assertTrue(responseStr.contains("<td>Mayer</td>")),
                () -> assertTrue(responseStr.contains("Delete"))
        );
    }
}
