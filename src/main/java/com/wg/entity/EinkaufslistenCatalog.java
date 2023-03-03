package com.wg.entity;

import com.wg.boundary.rest.acl.einkaufslistenEintrag.FullEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.POSTEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.PUTCheckboxDTO;

import java.util.List;
import java.util.Optional;

/**
 * @author tcirksen
 * */
public interface EinkaufslistenCatalog {

    List<FullEinkaufslistenEintragDTO> getAllEintraege(String wgName);
    Optional<FullEinkaufslistenEintragDTO> getEintragById(long einkaufslistenEintragId);
    FullEinkaufslistenEintragDTO updateEinkauflistenElement(long einkaufslistenEintragId, int menge, String item, String besteller);
    long deleteEinkaufslistenEintragById(long einkaufslistenEintragId);

    FullEinkaufslistenEintragDTO createEintragbyView(String wgName, POSTEinkaufslistenEintragDTO postEinkaufslistenEintragDTO);

    FullEinkaufslistenEintragDTO addEinkaufslistenElement(String wgName, boolean erledigt, int menge, String item, String besteller);

    PUTCheckboxDTO putCheckboxValue(PUTCheckboxDTO putCheckboxDTO);
}
