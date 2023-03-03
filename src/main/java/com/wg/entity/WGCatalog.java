package com.wg.entity;

import com.wg.boundary.rest.acl.einkaufslistenEintrag.PUTCheckboxDTO;
import com.wg.boundary.rest.acl.wg.POSTWGDTO;
import com.wg.boundary.rest.acl.wg.PUTWGDTO;
import com.wg.boundary.rest.acl.wg.ReturnWGDTO;
import com.wg.boundary.rest.acl.wg.UpdateWGNameDTO;

import java.util.List;

/**
 * @author majaesch & tcirksen
 *
 * */
public interface WGCatalog {
    List<ReturnWGDTO> getAllWG();
    ReturnWGDTO getWGbyId(Long id);
    ReturnWGDTO editWG(PUTWGDTO dto);
    ReturnWGDTO postWG(POSTWGDTO dto);
    boolean deleteWG(Long id);
    boolean deleteWGbyName(String wgName);
    boolean updateName(UpdateWGNameDTO updateWGNameDTO, String oldWGName);

}
