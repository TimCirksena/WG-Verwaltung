package com.wg.gateway.repo;

import com.wg.boundary.rest.acl.einkaufslistenEintrag.FullEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.kalender.POSTKEintragDTO;
import com.wg.boundary.rest.acl.kalender.ReturnKEintragDTO;
import com.wg.boundary.rest.exceptionMappers.CustomConstraintViolationException;
import com.wg.entity.EinkaufslistenEintrag;
import com.wg.entity.KalenderEintrag;
import com.wg.entity.KalenderEintragCatalog;
import com.wg.entity.WG;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author majaesch & tcirksen
 *
 * */
@ApplicationScoped
public class KalendereintragRepo implements KalenderEintragCatalog {

    /**
     * @return
     */
    @Override
    public List<ReturnKEintragDTO> debugGetALLKalenderEintraege() {
        List<KalenderEintrag> kalenderEintrags = KalenderEintrag.findAll().list();
        List<ReturnKEintragDTO> returnKEintragDTOS = new ArrayList<>();
        for (KalenderEintrag k : kalenderEintrags){
            returnKEintragDTOS.add(new ReturnKEintragDTO(k));
        }
        return returnKEintragDTOS;
    }

    /**
     * @param wgName
     * @param monday anfang der woche die man haben möchte
     * @return
     */
    @Override
    public List<ReturnKEintragDTO> getKalenderEintraegeWoche(String wgName, LocalDate monday) {
        List<LocalDate> wochentage = new ArrayList<>();
        //von montag bis sonntag alle tage adden
        wochentage.add(monday);
        wochentage.add(monday.plusDays(1));
        wochentage.add(monday.plusDays(2));
        wochentage.add(monday.plusDays(3));
        wochentage.add(monday.plusDays(4));
        wochentage.add(monday.plusDays(5));
        wochentage.add(monday.plusDays(6));

        //wir gehen alle tage durch, und querien die Datenbank, ob KEintraege zu diesem Tag vorhanden sind
        List<KalenderEintrag> eintraegeDerWoche = new ArrayList<>();
        for (LocalDate tag : wochentage){
            eintraegeDerWoche.addAll(KalenderEintrag.find("datum",tag).list());
        }
        //aus den Kalendereinträgen, DTOs machen
        List<ReturnKEintragDTO> returnDtos = new ArrayList<>();
        for (KalenderEintrag eintrag : eintraegeDerWoche){
            if(eintrag.getWg().getWgname().equals(wgName)) {
                returnDtos.add(new ReturnKEintragDTO(eintrag));
            }
        }

        return returnDtos;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ReturnKEintragDTO getKalenderEintragById(Long id) {
        return new ReturnKEintragDTO(KalenderEintrag.findById(id));
    }

    /**
     * @param wgName
     * @param dto
     * @return
     */
    @Override
    public ReturnKEintragDTO postKalenderEintrag(String wgName, POSTKEintragDTO dto) {
        try {
            KalenderEintrag kalenderEintrag = new KalenderEintrag(dto);
            kalenderEintrag.setWg(WG.find("wgname", wgName).firstResult());
            kalenderEintrag.persistAndFlush();
            return new ReturnKEintragDTO(kalenderEintrag);
        }catch (ConstraintViolationException c){
            throw new CustomConstraintViolationException(c.getMessage());
        }

    }

    /**
     * @param id
     * @param dto
     * @return
     */
    @Override
    public ReturnKEintragDTO editKalenderEintrag(Long id, POSTKEintragDTO dto) {
        try{
        KalenderEintrag kalenderEintrag = KalenderEintrag.findById(id);
        kalenderEintrag.setName(dto.name);
        kalenderEintrag.setDatum(dto.datum);
        kalenderEintrag.setStartTime(dto.startTime);
        kalenderEintrag.setEndTime(dto.endTime);
        kalenderEintrag.setEintragsArt(dto.eintragsArt);
        kalenderEintrag.setBeteiligte(new ArrayList<>(dto.beteiligte));
        kalenderEintrag.setBeschreibung(dto.beschreibung);

        KalenderEintrag.flush();
        return new ReturnKEintragDTO(kalenderEintrag);
    }catch (ConstraintViolationException c){
        throw new CustomConstraintViolationException(c.getMessage());
    }

}

    /**
     * @param id
     * @return
     */
    @Override
    public boolean deleteKalenderEintrag(Long id) {
        return KalenderEintrag.deleteById(id);
    }
}
