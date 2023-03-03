package com.wg.boundary.rest.acl.kalender;

import com.wg.entity.EintragsArt;
import com.wg.entity.KalenderEintrag;


import javax.json.bind.annotation.JsonbProperty;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnKEintragDTO {
    public long wgId;
    public String wgName;
    public long kalenderEintragId;
    public String name;
    public LocalDate datum;
    public LocalTime startTime;
    public LocalTime endTime;
    public EintragsArt eintragsArt;
    public List<String> beteiligte;
    public String beschreibung;

    public Integer anzahlGaeste;

    @JsonbProperty("_links")
    public Map<String, URI> links = new HashMap<>();

    /**
    * @author majaesch
    *
    * */
    public ReturnKEintragDTO() {
    }
    public ReturnKEintragDTO(KalenderEintrag eintrag){
        this.wgId = eintrag.getWg().getWgId();
        this.wgName = eintrag.getWg().getWgname();
        this.kalenderEintragId = eintrag.getKalenderEintragId();
        this.name = eintrag.getName();
        this.datum = eintrag.getDatum();
        this.startTime = eintrag.getStartTime();
        this.endTime = eintrag.getEndTime();
        this.eintragsArt = eintrag.getEintragsArt();
        this.beteiligte = new ArrayList<>(eintrag.getBeteiligte());
        this.beschreibung = eintrag.getBeschreibung();
        this.anzahlGaeste = eintrag.getAnzahlGaeste();
    }

    public void addURI(String name, URI uri){
        this.links.put(name,uri);
    }
}
