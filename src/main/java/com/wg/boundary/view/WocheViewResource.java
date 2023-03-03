package com.wg.boundary.view;

import com.wg.control.WocheViewInterface;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
/**
 * @author majaesch & tcirksen
 * */
@Path("/woche")
@Produces(MediaType.TEXT_HTML)
@ApplicationScoped
public class WocheViewResource {

    @Inject
    WocheViewInterface wocheViewInterface;

    @GET
    @Path("/aktuell")
    @RolesAllowed({"admin","wg"})
    @Transactional
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Wochen-VIEW: Gettet Aktuelle Woche", description = "Returned angeforderte Woche")
    public TemplateInstance getAktuelleWoche(@Context SecurityContext securityContext){
        return wocheViewInterface.getAktuelleWoche(securityContext);
    }

    @GET
    @Path("/{jahr}/{kw}")
    @RolesAllowed({"admin","wg"})
    @Transactional
    @Retry(maxRetries = 4)
    @Timeout(250)
    @Operation(summary = "Wochen-VIEW: Gettet Woche nach Jahr und KWQ", description = "Returned Woche die zum Jahr und zur KW passt")
    public TemplateInstance getWocheMitJahrUndKW(@Context SecurityContext securityContext, @PathParam("jahr") Integer jahr, @PathParam("kw") Integer kw){
        return wocheViewInterface.getWocheMitJahrUndKW(securityContext,jahr,kw);
    }
}
