package com.wg.boundary.view;


import com.wg.boundary.rest.acl.wg.DeleteWGDTO;
import com.wg.boundary.rest.acl.wg.UpdateWGNameDTO;
import com.wg.control.WGInterface;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
/**
 * @author tcirksen
 * */
@Path("/menu")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@ApplicationScoped
public class MenuViewResource {

    @Inject
    Template optionen_view;

    @Inject
    Template menu_view;

    @Inject
    WGInterface wgInterface;

    @GET
    @Transactional
    @Path("/optionen")
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Optionen-VIEW: Gettet Seite", description = "Returned die Optionen View")
    public TemplateInstance getOptions() {
        return optionen_view.instance();
    }

    @GET
    @Transactional
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Menu-VIEW: Gettet Seite", description = "Returned die Menu View")
    public TemplateInstance getMenu(@Context SecurityContext securityContext) {
        String wgName = securityContext.getUserPrincipal().getName();
        System.out.println(wgName);
        return menu_view.data("wgname", wgName);
    }

    @PATCH
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Optionen-VIEW: WG umbenenen", description = "Methode dient dazu eine WG umzubenenen, danach ist ein neuer login erforderlich")
    public Response updateName(UpdateWGNameDTO updateWGNameDTO, @Context SecurityContext securityContext) {
        return wgInterface.updateName(updateWGNameDTO, securityContext.getUserPrincipal().getName());
    }

    @DELETE
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //@Path("/WGDelete")
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Optionen-VIEW: Löscht die aktuelle WG", description = "Aktuelle WG wird per button klick gelöscht, danach ist ein neuer login erforderlich")
    public Response deleteWG( @Context SecurityContext securityContext) {
        return wgInterface.deleteWGbyName(securityContext.getUserPrincipal().getName());
    }
}

