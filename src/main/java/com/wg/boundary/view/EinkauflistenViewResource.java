package com.wg.boundary.view;

import com.wg.boundary.rest.acl.einkaufslistenEintrag.DeleteEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.POSTEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.PUTCheckboxDTO;
import com.wg.boundary.websocket.EinkaufslistenWebsocket;
import com.wg.control.EinkaufslistenInterface;
import com.wg.control.EinkaufslistenManagement;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * @author tcirksen
 * */

@Path("/einkaufsliste")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@ApplicationScoped
public class EinkauflistenViewResource {

    @Inject
    EinkaufslistenInterface einkaufslistenInterface;

    @Inject
    EinkaufslistenWebsocket einkaufslistenWebsocket;

    @GET
    @Transactional
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Einkauflisten-VIEW: Get all EinkaufslistenEinträge", description = "Returned alle EinkaufslistenEinträge für die TemplateInstance, somit können sie per qute angezeigt werden")
    public TemplateInstance getAllEintraeges(@Context SecurityContext securityContext){
        return einkaufslistenInterface.getAllEintraegeTemplate(securityContext.getUserPrincipal().getName());
    }
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Einkauflisten-VIEW: Erstellt EinkaufslistenEintrag", description = "Neue EinkaufslistenEinträge werden mittels modal und post request erstellt")
    public Response createEintragByView(@Context SecurityContext securityContext, POSTEinkaufslistenEintragDTO postEinkaufslistenEintragDTO){
        return einkaufslistenInterface.createEintragbyView(securityContext.getUserPrincipal().getName(),postEinkaufslistenEintragDTO);
    }
    @DELETE
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Einkauflisten-VIEW: Löschen EinkaufslistenEintrag", description = "Löscht einen Eintrag direkt in der view")
    public Response deleteEinkaufslistenElementById(DeleteEinkaufslistenEintragDTO deleteEinkaufslistenEintragDTO){
        return einkaufslistenInterface.deleteEinkaufslistenEintragById(deleteEinkaufslistenEintragDTO.eintragId);
    }
    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Einkauflisten-VIEW: Checkbox status ändern", description = "Ändert den aktuellen status der checkbox")
    public Response putCheckboxValue(PUTCheckboxDTO putCheckboxDTO){
        System.out.println(putCheckboxDTO.einkauflistenEintragId + "  " + putCheckboxDTO.erledigt );
        return einkaufslistenInterface.putCheckboxValue(putCheckboxDTO);
    }
}
