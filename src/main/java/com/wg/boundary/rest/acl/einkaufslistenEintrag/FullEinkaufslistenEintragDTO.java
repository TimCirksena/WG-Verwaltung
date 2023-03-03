package com.wg.boundary.rest.acl.einkaufslistenEintrag;

import com.wg.entity.EinkaufslistenEintrag;

import javax.json.bind.annotation.JsonbProperty;
import javax.ws.rs.core.Link;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tcirksen
 * */
public class FullEinkaufslistenEintragDTO {

    public long wgId;
    public long einkaufslistenEintragId;
    public boolean erledigt;
    public int menge;
    public String item;
    public String besteller;
    @JsonbProperty("_links")
    public Map<String, URI> links = new HashMap<>();

    public FullEinkaufslistenEintragDTO() {
    }

    public FullEinkaufslistenEintragDTO(EinkaufslistenEintrag einkaufslistenEintrag){
        this.wgId = einkaufslistenEintrag.getWg().getWgId();
        this.einkaufslistenEintragId = einkaufslistenEintrag.getEinkaufslisteEintragId();
        this.erledigt = einkaufslistenEintrag.isErledigt();
        this.menge = einkaufslistenEintrag.getMenge();
        this.item = einkaufslistenEintrag.getItem();
        this.besteller = einkaufslistenEintrag.getBesteller();
    }
    public FullEinkaufslistenEintragDTO(long einkaufslistenEintragId,boolean erledigt,  int menge, String item, String besteller){
        this.einkaufslistenEintragId = einkaufslistenEintragId;
        this.erledigt = erledigt;
        this.menge = menge;
        this.item = item;
        this.besteller = besteller;
    }

    @Override
    public String toString() {
        return "FullEinkaufslistenEintragDTO{" +
                "einkaufslistenEintragId=" + einkaufslistenEintragId +
                ", erledigt=" + erledigt +
                ", menge=" + menge +
                ", item='" + item + '\'' +
                ", besteller='" + besteller + '\'' +
                '}';
    }

    public void addURI(String name, URI uri){
        this.links.put(name,uri);
    }
}
