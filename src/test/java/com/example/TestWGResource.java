package com.example;

import com.wg.boundary.rest.acl.wg.POSTWGDTO;
import com.wg.boundary.rest.acl.wg.PUTWGDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * @author tcirksen
 * */

@QuarkusTest
public class TestWGResource {

    @Test
    @TestSecurity(user = "user", roles = {"admin", "user"})
    public void testGetWGbyId(){

        given()
                .when().get("/wgverwaltung/"+1L)
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "user", roles = {"admin", "user"})
    public void testPostWG(){

        POSTWGDTO postwgdto = new POSTWGDTO();
        postwgdto.wgName = "peter";
        postwgdto.password = "lustig";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(postwgdto)
                .when().post("/wgverwaltung")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("peter", response.jsonPath().getString("wgName"));
    }
    @Test
    @TestSecurity(user = "user", roles = {"admin", "user"})
    public void testEditWG(){

        PUTWGDTO putwgdto = new PUTWGDTO();
        putwgdto.wgName = "fritz";
        putwgdto.password = "fuchs";
        putwgdto.wgId = 1L;

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(putwgdto)
                .when().put("/wgverwaltung")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("fritz", response.jsonPath().getString("wgName"));
    }
}