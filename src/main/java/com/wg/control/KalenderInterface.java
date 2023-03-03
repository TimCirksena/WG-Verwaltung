package com.wg.control;

import com.wg.boundary.rest.acl.kalender.POSTKEintragDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * @author majaesch & tcirksen
 *
 * */
public interface KalenderInterface {
    Response debugGetALLKalenderEintraege();
    Response getKalenderEintraegeWoche(Integer year, Integer calendarWeek,SecurityContext securityContext);
    Response getKalenderEintragById(Long id);
    Response postKalenderEintrag(SecurityContext securityContext, POSTKEintragDTO dto);
    Response editKalenderEintrag(Long id, POSTKEintragDTO dto);
    Response deleteKalenderEintrag(Long id);
}
