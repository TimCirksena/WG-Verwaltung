package com.wg.control;

import com.wg.boundary.rest.acl.wg.POSTWGDTO;
import com.wg.boundary.rest.acl.wg.PUTWGDTO;
import com.wg.boundary.rest.acl.wg.ReturnWGDTO;
import com.wg.boundary.rest.acl.wg.UpdateWGNameDTO;
import com.wg.entity.WGCatalog;
import com.wg.shared.ResourceUriBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * @author majaesch
 * */
@ApplicationScoped
public class WGManagement implements WGInterface{
    @Inject
    WGCatalog wgCatalog;

    @Context
    UriInfo uriInfo;
    @Inject
    ResourceUriBuilder resourceUriBuilder;

    /**
     * @return
     */
    @Override
    public Response getAllWG() {
        List<ReturnWGDTO> returnWGDTOList = wgCatalog.getAllWG();
        for (ReturnWGDTO returnWGDTO : returnWGDTOList){
            addSelfLinkToWGDTO(returnWGDTO);
        }
        return Response.ok(returnWGDTOList).build();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Response getWGbyId(Long id) {
        ReturnWGDTO returnWGDTO = wgCatalog.getWGbyId(id);
        addSelfLinkToWGDTO(returnWGDTO);
        return Response.ok(returnWGDTO).build();
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public Response editWG(PUTWGDTO dto) {
        try{
            ReturnWGDTO returnWGDTO =wgCatalog.editWG(dto);
            addSelfLinkToWGDTO(returnWGDTO);
            return Response.ok(returnWGDTO).build();
        } catch (ConstraintViolationException c){
            return Response.status(409, "needed fields are empty").build();
        }
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public Response postWG(POSTWGDTO dto) {
        try{
            ReturnWGDTO returnWGDTO =wgCatalog.postWG(dto);
            addSelfLinkToWGDTO(returnWGDTO);
            return Response.ok(returnWGDTO).build();
        } catch (ConstraintViolationException e){
            return Response.status(409,"needed fields are empty").build();
        } catch (PersistenceException p){
            return Response.status(422).build();
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Response deleteWG(Long id) {
        if(wgCatalog.deleteWG(id)){
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response deleteWGbyName(String wgName) {
        if(wgCatalog.deleteWGbyName(wgName)){
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response updateName(UpdateWGNameDTO updateWGNameDTO, String oldWGName){
        if(wgCatalog.updateName(updateWGNameDTO,oldWGName)){
            return Response.ok().build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private void addSelfLinkToWGDTO(ReturnWGDTO returnWGDTO){
        URI selfUri = this.resourceUriBuilder.forWG(returnWGDTO.wgId, uriInfo);
        //Link link = Link.fromUri(selfUri)
        //        .rel("self")
        //        .type(MediaType.APPLICATION_JSON)
        //        .build();
        returnWGDTO.addURI("self",selfUri);
    }
}
