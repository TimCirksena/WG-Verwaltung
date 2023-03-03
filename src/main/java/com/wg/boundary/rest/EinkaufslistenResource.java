package com.wg.boundary.rest;

import com.wg.boundary.rest.acl.einkaufslistenEintrag.FullEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.POSTEinkaufslistenEintragDTO;
import com.wg.control.EinkaufslistenInterface;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

/**
 * @author tcirksen
 * */

@Path("/wgverwaltung/einkaufsliste")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EinkaufslistenResource {

    @Inject
    EinkaufslistenInterface einkaufslistenService;

    @GET
    @Transactional
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Alle Einträge werden geholt", description = "Returned eine Liste aus FullEinkaufslistenEintragDTOs")
    public Response getAllEintraege(@Context SecurityContext securityContext) {
        return einkaufslistenService.getAllEintraege(securityContext.getUserPrincipal().getName());
    }

    @GET
    @Transactional
    @Path("/{einkaufslistenEintragId}")
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Ein Eintrag von ID", description = "Holt einen Eintrag by Id")
    public Response getEintragById(@PathParam("einkaufslistenEintragId") long einkaufslistenEintragId) {
        return einkaufslistenService.getEintragById(einkaufslistenEintragId);
    }

    @PUT
    @Transactional
    @Path("/{einkaufslistenEintragId}")
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Ändert bestehendes Element", description = "Ändert ein bestehendes EinkauflistenEintag Element")
    public Response updateEinkauflistenElement(@PathParam("einkaufslistenEintragId") long einkaufslistenEintragId, POSTEinkaufslistenEintragDTO postEinkaufslistenEintragDTO) {
        return einkaufslistenService.updateEinkauflistenElement(einkaufslistenEintragId, postEinkaufslistenEintragDTO.menge, postEinkaufslistenEintragDTO.item, postEinkaufslistenEintragDTO.besteller);
    }
    @DELETE
    @Transactional
    @Path("/{einkaufslistenEintragId}")
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Delete by ID", description = "Löscht ein Element mit der übereichten Id")
    public Response deleteEinkaufslistenEintragById(@PathParam("einkaufslistenEintragId") long einkaufslistenEintragId) {
        return einkaufslistenService.deleteEinkaufslistenEintragById(einkaufslistenEintragId);
    }
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Add Element by postElementDTO", description = "Addet ein Element an die eingeloggte WG")
    public Response addEinkaufslistenElement(@Context SecurityContext securityContext, POSTEinkaufslistenEintragDTO postEinkaufslistenEintragDTO) {
        return einkaufslistenService.addEinkaufslistenElement(
                securityContext.getUserPrincipal().getName(),
                postEinkaufslistenEintragDTO.erledigt,
                postEinkaufslistenEintragDTO.menge,
                postEinkaufslistenEintragDTO.item,
                postEinkaufslistenEintragDTO.besteller);
    }
}
