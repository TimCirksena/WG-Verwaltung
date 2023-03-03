package com.example;



import com.wg.boundary.rest.acl.kalender.POSTKEintragDTO;
import com.wg.boundary.rest.acl.wg.POSTWGDTO;
import com.wg.boundary.rest.acl.wg.PUTWGDTO;
import com.wg.boundary.rest.payloads.ExamplePayloads;
import com.wg.entity.KalenderEintrag;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.security.RolesAllowed;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.wg.entity.EintragsArt.KOCHEN;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
public class TestKalenderResource {
    @Test
    @Transactional
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testGetKalendarEintragsById(){
/*
        KalenderEintrag kalenderEintrag = new KalenderEintrag();
        kalenderEintrag.setEintragsArt(KOCHEN);
        kalenderEintrag.setBeschreibung("Lecker");
        kalenderEintrag.persist();*/


        given()
                .when().get("/wgverwaltung/kalender/"+1L)
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testPostKalenderEintrag500(){
        //Leeres Objebt sollte nicht übergeben werden, deswegen 500 test
        POSTKEintragDTO postkEintragDTO = new POSTKEintragDTO();

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(postkEintragDTO)
                .when().post("/wgverwaltung/kalender")
                .then()
                .extract().response();

        Assertions.assertEquals(500, response.statusCode());

    }
    @Test
    @TestSecurity(user = "user", roles = {"admin", "user"})
    public void testPostKalenderEintrag200(){

        List<String> beteiligte = new ArrayList<>();
        beteiligte.add("Tim");
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.NOON;
        POSTKEintragDTO postkEintragDTO = new POSTKEintragDTO();

        postkEintragDTO.name = "Essen gehen";
        postkEintragDTO.eintragsArt = KOCHEN;
        postkEintragDTO.beschreibung = "Wir kochen selbst";
        postkEintragDTO.beteiligte = beteiligte;
        postkEintragDTO.datum = localDate;
        postkEintragDTO.endTime = localTime.plusHours(2L);
        postkEintragDTO.startTime = localTime;

        Jsonb jsonb = JsonbBuilder.create();
        String resultJson = jsonb.toJson(postkEintragDTO);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(resultJson)
                .when().post("/wgverwaltung/kalender")
                .then()
                .extract().response();

        System.out.println(response.jsonPath().getString("message"));
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Essen gehen", response.jsonPath().getString("name"));

    }
}
