package com.wg.shared;

import com.wg.boundary.rest.EinkaufslistenResource;
import com.wg.boundary.rest.KalenderResource;
import com.wg.boundary.rest.WGResource;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
//Quelle: Beispiel aus der Vorlesung von Herrn Roosmann
//Verantwortlicher: majaesch

@ApplicationScoped
public class ResourceUriBuilder {

    public URI forKEintrag(Long id, UriInfo uriInfo) {
        return createResourceUri(KalenderResource.class, "getKalenderEintragById", id, uriInfo);
    }

    public URI forWG(Long id, UriInfo uriInfo){
        return createResourceUri(WGResource.class, "getWGbyId",id,uriInfo);
    }

    public URI forEEintrag(Long id, UriInfo uriInfo){
        return createResourceUri(EinkaufslistenResource.class, "getEintragById",id,uriInfo);
    }

    private URI createResourceUri(Class<?> resourceClass, String method, Long id, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).path(resourceClass, method).build(id);
    }
}
