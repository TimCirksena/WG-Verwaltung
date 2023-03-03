package com.wg.boundary.rest;

import com.wg.boundary.rest.acl.kalender.POSTKEintragDTO;
import com.wg.boundary.rest.acl.kalender.ReturnKEintragDTO;
import com.wg.boundary.websocket.WochenWebsocket;
import com.wg.control.KalenderInterface;
import com.wg.entity.KalenderEintrag;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import static com.wg.boundary.rest.payloads.ExamplePayloads.*;
/**
 * @author majaesch & tcirksen
 *
 * */
@ApplicationScoped
@Path("/wgverwaltung/kalender")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KalenderResource {
    @Inject
    KalenderInterface kalenderInterface;

    @GET
    @Transactional
    @Path("/debug")
    @RolesAllowed("admin")
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Debug Funktion", description = "Für Admins: Alle Einträge von allen WGs werden gesehen")
    public Response debugGetALLKalenderEintraege(){
        return kalenderInterface.debugGetALLKalenderEintraege();
    }


    /**
     * @param calendarWeek die KW des Jahres
     * @param year das gewünschte Jahr
     */
    @GET
    @Transactional
    @Path("/{year}/{calendarWeek}")
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Returned eine KW", description = "Eine Woche wird mittels Jahr und KW gesucht und returned")
    public Response getKalenderEintraegeWoche(@PathParam("year") Integer year, @PathParam("calendarWeek") Integer calendarWeek, @Context SecurityContext securityContext) {
        return kalenderInterface.getKalenderEintraegeWoche(year, calendarWeek, securityContext);
    }

    @GET
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Get KalenderEintrag", description = "Ein KalenderEintrag wird mittels der ID geholt")
    public Response getKalenderEintragById(@PathParam("id") Long id) {
        return kalenderInterface.getKalenderEintragById(id);
    }

    /**
     * der Request body musste angepasst werden, da der Swagger vorschlag von LocalTime fehlerhaft ist
     * Quelle der Request Body annotation: <a href="https://stackoverflow.com/questions/71464876/how-to-generate-a-request-example-in-swagger-with-quarkus">...</a>
     */
    @POST
    @Transactional
    @RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = POSTKEintragDTO.class, required = true, requiredProperties = {"name"}),
                    examples = {@ExampleObject(
                            name = "Example Value1",
                            description = "neuer kalendereintrag",
                            value = EXAMPLE_KALENDER_EINTRAG
                    ), @ExampleObject(
                            name = "Example Value2",
                            description = "neuer kalendereintrag",
                            value = EXAMPLE_KALENDER_EINTRAG_2)
                            , @ExampleObject(
                            name = "Example Value3",
                            description = "neuer kalendereintrag",
                            value = EXAMPLE_KALENDER_EINTRAG_3)
                            , @ExampleObject(
                            name = "Example Value4",
                            description = "neuer kalendereintrag",
                            value = EXAMPLE_KALENDER_EINTRAG_4)
                    }
            ))
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Erstellen Kalender Eintrag", description = "Ein Kalender Eintrag wird aus einem PostKEintragDTO erstellt")
    public Response postKalenderEintrag(@Context SecurityContext securityContext, POSTKEintragDTO dto) {
        return kalenderInterface.postKalenderEintrag(securityContext, dto);
    }

    @PUT
    @Transactional
    @RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = POSTKEintragDTO.class, required = true, requiredProperties = {"name"}),
                    examples = {@ExampleObject(
                            name = "Example Value1",
                            description = "neuer kalendereintrag",
                            value = EXAMPLE_KALENDER_EINTRAG
                    ), @ExampleObject(
                            name = "Example Value2",
                            description = "neuer kalendereintrag",
                            value = EXAMPLE_KALENDER_EINTRAG_2)
                    , @ExampleObject(
                            name = "Example Value3",
                            description = "neuer kalendereintrag",
                            value = EXAMPLE_KALENDER_EINTRAG_3)
                    , @ExampleObject(
                            name = "Example Value4",
                            description = "neuer kalendereintrag",
                            value = EXAMPLE_KALENDER_EINTRAG_4)
                    }
            ))
    @RolesAllowed({"admin","wg"})
    @Path("/{id}")
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Editieren des Eintrags", description = "Durch übereichen der id und eines POSTKEintragDTOs wird ein Eintrag geändert")
    public Response editKalenderEintrag(@PathParam("id") Long id, POSTKEintragDTO dto) {
        return kalenderInterface.editKalenderEintrag(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Delete eines Eintrags", description = "Löschen mit der Id des Eintrags")
    public Response deleteKalenderEintrag(@PathParam("id") Long id) {
        return kalenderInterface.deleteKalenderEintrag(id);
    }
}
