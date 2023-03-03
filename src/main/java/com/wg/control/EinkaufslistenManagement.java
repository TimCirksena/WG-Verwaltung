package com.wg.control;

import com.wg.boundary.rest.acl.einkaufslistenEintrag.FullEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.POSTEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.PUTCheckboxDTO;
import com.wg.boundary.websocket.EinkaufslistenWebsocket;
import com.wg.entity.EinkaufslistenCatalog;
import com.wg.shared.ResourceUriBuilder;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
/**
 * @author tcirksen, majaesch
 * */

@ApplicationScoped
public class EinkaufslistenManagement implements  EinkaufslistenInterface{

    @Inject
    EinkaufslistenCatalog einkaufslistenCatalog;

    @Inject
    EinkaufslistenWebsocket einkaufslistenWebsocket;
    @Inject
    Template einkaufslisten_view;

    @Context
    UriInfo uriInfo;
    @Inject
    ResourceUriBuilder resourceUriBuilder;
    @Override
    public Response createEintragbyView(String wgName, POSTEinkaufslistenEintragDTO postEinkaufslistenEintragDTO) {
        try {
            einkaufslistenWebsocket.eintragCreate(einkaufslistenCatalog.createEintragbyView(wgName,postEinkaufslistenEintragDTO));
            return Response.ok().build();
        } catch (ConstraintViolationException e) {
            return Response.status(409, "needed fields are empty").build();
        }
    }

    @Override
    public Response getAllEintraege(String wgName) {
        List<FullEinkaufslistenEintragDTO> dtos = einkaufslistenCatalog.getAllEintraege(wgName);
        for (FullEinkaufslistenEintragDTO f : dtos){
            addSelfLinkToEinkaufslistenEintragDTO(f);
        }
        return Response.ok(dtos).build();
    }

    @Override
    public TemplateInstance getAllEintraegeTemplate(String wgName){
        return einkaufslisten_view.data("einkaufslisten", einkaufslistenCatalog.getAllEintraege(wgName));
    }

    @Override
    public Response getEintragById(long einkaufslistenEintragId) {
        Optional<FullEinkaufslistenEintragDTO> eintrag = einkaufslistenCatalog.getEintragById(einkaufslistenEintragId);
        if(eintrag.isPresent()){
            FullEinkaufslistenEintragDTO einkaufslistenEintragDTO = eintrag.get();
            addSelfLinkToEinkaufslistenEintragDTO(einkaufslistenEintragDTO);
            return Response.ok(einkaufslistenEintragDTO).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response updateEinkauflistenElement(long einkaufslistenEintragId, int menge, String item, String besteller) {
        try {
            FullEinkaufslistenEintragDTO fullEinkaufslistenEintragDTO = einkaufslistenCatalog.updateEinkauflistenElement(einkaufslistenEintragId,menge,item,besteller);
            addSelfLinkToEinkaufslistenEintragDTO(fullEinkaufslistenEintragDTO);
            return Response.ok(fullEinkaufslistenEintragDTO).build();
        } catch (ConstraintViolationException e) {
            return Response.status(409, "needed fields are empty").build();
        }
    }

    @Override
    public Response deleteEinkaufslistenEintragById(long einkaufslistenEintragId) {
        try {
            long deletedId = einkaufslistenCatalog.deleteEinkaufslistenEintragById(einkaufslistenEintragId);
            einkaufslistenWebsocket.eintragDelete(deletedId);
            return Response.ok(deletedId).build();
        } catch (ConstraintViolationException e) {
            return Response.status(409, "needed fields are empty").build();
        }
    }

    @Override
    public Response addEinkaufslistenElement(String wgName, boolean erledigt, int menge, String item, String besteller) {
        try {
            FullEinkaufslistenEintragDTO einkaufslistenEintragDTO = einkaufslistenCatalog.addEinkaufslistenElement(wgName,erledigt,menge,item,besteller);
            addSelfLinkToEinkaufslistenEintragDTO(einkaufslistenEintragDTO);
            return Response.ok(einkaufslistenEintragDTO).build();
        } catch (ConstraintViolationException e) {
            return Response.status(409, "needed fields are empty").build();
        }
    }

    @Override
    public Response putCheckboxValue(PUTCheckboxDTO putCheckboxDTO) {
        try {
            return Response.ok(einkaufslistenCatalog.putCheckboxValue(putCheckboxDTO)).build();
        } catch (ConstraintViolationException e) {
            return Response.status(409, "needed fields are empty").build();
        }
    }

    private void addSelfLinkToEinkaufslistenEintragDTO(FullEinkaufslistenEintragDTO fullEinkaufslistenEintragDTO){
        URI selfUri = this.resourceUriBuilder.forEEintrag(fullEinkaufslistenEintragDTO.einkaufslistenEintragId, uriInfo);
        //System.out.println(selfUri);
        /*
        Link link = Link.fromUri(selfUri)
                .build();*/
        fullEinkaufslistenEintragDTO.addURI("self",selfUri);
    }
}
