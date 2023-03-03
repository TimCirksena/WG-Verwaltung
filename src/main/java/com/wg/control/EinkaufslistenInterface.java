package com.wg.control;

import com.wg.boundary.rest.acl.einkaufslistenEintrag.FullEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.POSTEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.PUTCheckboxDTO;
import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
/**
 * @author tcirksen
 * */
public interface EinkaufslistenInterface {

    Response createEintragbyView(String wgName, POSTEinkaufslistenEintragDTO postEinkaufslistenEintragDTO);
    Response getAllEintraege(String wgName);
    Response getEintragById(long einkaufslistenEintragId);
    Response updateEinkauflistenElement(long einkaufslistenEintragId, int menge, String item, String besteller);
    Response deleteEinkaufslistenEintragById(long einkaufslistenEintragId);
    TemplateInstance getAllEintraegeTemplate(String wgName);
    Response addEinkaufslistenElement(String wgName,boolean erledigt, int menge, String item, String besteller);

    Response putCheckboxValue(PUTCheckboxDTO putCheckboxDTO);
}
