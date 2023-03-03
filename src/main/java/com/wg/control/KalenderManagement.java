package com.wg.control;

import com.wg.boundary.rest.acl.kalender.POSTKEintragDTO;
import com.wg.boundary.rest.acl.kalender.ReturnKEintragDTO;
import com.wg.boundary.websocket.WochenWebsocket;
import com.wg.entity.KalenderEintragCatalog;
import com.wg.shared.ResourceUriBuilder;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
/***
 * @author majaesch
 */

@ApplicationScoped
public class KalenderManagement implements KalenderInterface {
    @Inject
    KalenderEintragCatalog kalenderEintragCatalog;

    @Inject
    WochenWebsocket wochenWebsocket;

    @Context
    UriInfo uriInfo;
    @Inject
    ResourceUriBuilder resourceUriBuilder;

    /**
     * @return
     */
    @Override
    public Response debugGetALLKalenderEintraege() {
        List<ReturnKEintragDTO> dtos = kalenderEintragCatalog.debugGetALLKalenderEintraege();
        for (ReturnKEintragDTO r : dtos){
            addSelfLinkToKEintragDTO(r);
        }
        return Response.ok(dtos).build();
    }

    @Override
    public Response getKalenderEintraegeWoche(Integer year, Integer calendarWeek, SecurityContext securityContext) {

        LocalDate monday = LocalDate.of(year, Month.JUNE, 1)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        List<ReturnKEintragDTO> dtos = kalenderEintragCatalog.getKalenderEintraegeWoche(securityContext.getUserPrincipal().getName(),monday);
        for (ReturnKEintragDTO r : dtos){
            addSelfLinkToKEintragDTO(r);
        }
        return Response.ok(dtos).build();
    }

    @Override
    public Response getKalenderEintragById(Long id) {
        ReturnKEintragDTO returnKEintragDTO = kalenderEintragCatalog.getKalenderEintragById(id);
        addSelfLinkToKEintragDTO(returnKEintragDTO);
        return Response.ok(returnKEintragDTO).build();
    }

    @Override
    public Response postKalenderEintrag(SecurityContext securityContext, POSTKEintragDTO dto) {
        try {
            ReturnKEintragDTO fertigerEintrag =kalenderEintragCatalog.postKalenderEintrag(securityContext.getUserPrincipal().getName(), dto);
            wochenWebsocket.wochenEintagCreate(fertigerEintrag);
            addSelfLinkToKEintragDTO(fertigerEintrag);
            return Response.ok(fertigerEintrag).build();
        } catch (ConstraintViolationException e) {
            JsonObject typeHelper = new JsonObject();
            typeHelper.put("message", e.getMessage());
            return Response.status(409).entity(typeHelper.toString()).build();
        }
    }

    @Override
    public Response editKalenderEintrag(Long id, POSTKEintragDTO dto) {
        try {
            ReturnKEintragDTO fertigerEintrag = kalenderEintragCatalog.editKalenderEintrag(id,dto);
            wochenWebsocket.wochenEintragUpdate(fertigerEintrag);
            addSelfLinkToKEintragDTO(fertigerEintrag);
            return Response.ok(fertigerEintrag).build();
        } catch (ConstraintViolationException e) {
            return Response.status(409, "needed fields are empty").build();
        }
    }

    @Override
    public Response deleteKalenderEintrag(Long id) {
        try {
            wochenWebsocket.wochenEintragDelete(id);
            return Response.ok(kalenderEintragCatalog.deleteKalenderEintrag(id)).build();
        } catch (ConstraintViolationException e) {
            return Response.status(409, "needed fields are empty").build();
        }
    }

    private void addSelfLinkToKEintragDTO(ReturnKEintragDTO returnKEintragDTO){
        URI selfUri = this.resourceUriBuilder.forKEintrag(returnKEintragDTO.kalenderEintragId, uriInfo);
        //Link link = Link.fromUri(selfUri)
        //        .rel("self")
        //        .type(MediaType.APPLICATION_JSON)
        //        .build();
        returnKEintragDTO.addURI("self",selfUri);
    }
}
