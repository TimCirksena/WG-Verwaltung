package com.wg.entity;

import com.wg.boundary.rest.acl.kalender.POSTKEintragDTO;
import com.wg.boundary.rest.acl.kalender.ReturnKEintragDTO;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

/**
 * @author majaesch
 *
 * */
public interface KalenderEintragCatalog {
    List<ReturnKEintragDTO> debugGetALLKalenderEintraege();
    List<ReturnKEintragDTO> getKalenderEintraegeWoche(String WGname, LocalDate monday);
    ReturnKEintragDTO getKalenderEintragById(Long id);
    ReturnKEintragDTO postKalenderEintrag(String wgName, POSTKEintragDTO dto);
    ReturnKEintragDTO editKalenderEintrag(Long id, POSTKEintragDTO dto);
    boolean deleteKalenderEintrag( Long id);
}
