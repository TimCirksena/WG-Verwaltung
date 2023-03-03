package com.wg.control;

import com.wg.boundary.rest.acl.wg.POSTWGDTO;
import com.wg.boundary.rest.acl.wg.PUTWGDTO;
import com.wg.boundary.rest.acl.wg.UpdateWGNameDTO;

import javax.ws.rs.core.Response;
/**
 * @author majaesch & tcirksen
 *
 * */
public interface WGInterface {
    Response getAllWG();
    Response getWGbyId(Long id);
    Response editWG(PUTWGDTO dto);
    Response postWG(POSTWGDTO dto);
    Response deleteWG(Long id);

    Response deleteWGbyName(String wgName);
    Response updateName(UpdateWGNameDTO updateWGNameDTO, String oldWGName);
}
