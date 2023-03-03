package com.example;


import com.wg.boundary.rest.acl.einkaufslistenEintrag.FullEinkaufslistenEintragDTO;
import com.wg.control.EinkaufslistenInterface;
import com.wg.entity.EinkaufslistenEintrag;
import com.wg.entity.WG;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author tcirksen
 * */

@QuarkusTest
public class TestEinkauflistenResource {

    @Inject
    EinkaufslistenInterface einkaufslistenService;

    @Test
    @Transactional
    @TestSecurity(user = "user", roles = {"admin", "user"})
    public void testEintragById() {
        Response response = given()
                .header("Content-type", "application/json")
                .when().get("/wgverwaltung/einkaufsliste/" + 1)
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Kokosmilsch", response.jsonPath().getString("item"));
    }
    @Test
    @TestSecurity(user = "user", roles = {"admin", "wg"})
    public void testaddEinkaufslistenElement(){

        FullEinkaufslistenEintragDTO fullEinkaufslistenEintragDTO = new FullEinkaufslistenEintragDTO();
        fullEinkaufslistenEintragDTO.einkaufslistenEintragId = 1;
        fullEinkaufslistenEintragDTO.erledigt = false;
        fullEinkaufslistenEintragDTO.menge = 2;
        fullEinkaufslistenEintragDTO.item = "Cola Zero";
        fullEinkaufslistenEintragDTO.besteller = "Maximillian";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(fullEinkaufslistenEintragDTO)
                .when().post("/wgverwaltung/einkaufsliste")
                .then()
                .extract().response();
        //201 für "CREATED" return
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Cola Zero", response.jsonPath().getString("item"));
        Assertions.assertEquals("Maximillian", response.jsonPath().getString("besteller"));
    }
}
