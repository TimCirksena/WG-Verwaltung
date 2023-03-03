package com.wg.boundary.rest;

import com.wg.boundary.rest.acl.wg.POSTWGDTO;
import com.wg.boundary.rest.acl.wg.PUTWGDTO;
import com.wg.control.WGInterface;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * @author majaesch & tcirksen
 *
 * */
@Path("/wgverwaltung")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WGResource {
    @Inject
    WGInterface wgInterface;

    @GET
    @Transactional
    @RolesAllowed({"admin"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Alle WGs werden gegettet", description = "Returned eine Liste aus allen vorhandenen WGs")
    public Response getAllWG() {
        return wgInterface.getAllWG();
    }

    @GET
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Get WG by Id", description = "Eine WG wird mittels ihrer Id returned")
    public Response getWGbyId(@PathParam("id") Long id) {
        return wgInterface.getWGbyId(id);
    }

    @PUT
    @Transactional
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Editieren der WG", description = "WG wird mittels PUTWGDTO geändert")
    public Response editWG(PUTWGDTO dto) {
        return wgInterface.editWG(dto);
    }

    /**
     * hier fehlt noch das abfangen wenn der Wg name schon belegt ist!
     * */
    @POST
    @Transactional
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Erstellen einer WG", description = "Eine neue WG wird erstellt mittels des POSTWGDTO")
    public Response postWG(POSTWGDTO dto) {
        return wgInterface.postWG(dto);
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"admin","wg"})
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Delete WG by Id", description = "Eine WG wird mittels ihrer Id gelöscht")
    public Response deleteWG(@PathParam("id")Long id) {
        return wgInterface.deleteWG(id);
    }
}
