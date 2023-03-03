package com.wg.gateway.repo;

import com.wg.boundary.rest.acl.einkaufslistenEintrag.FullComparator;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.FullEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.POSTEinkaufslistenEintragDTO;
import com.wg.boundary.rest.acl.einkaufslistenEintrag.PUTCheckboxDTO;
import com.wg.boundary.rest.exceptionMappers.CustomConstraintViolationException;
import com.wg.entity.EinkaufslistenCatalog;
import com.wg.entity.EinkaufslistenEintrag;
import com.wg.entity.WG;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author tcirksen
 */
@ApplicationScoped
public class EinkaufslistenRepo implements EinkaufslistenCatalog {
    //Returned alle einträge

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        //EinkaufslistenEintrag.deleteAll();
    }

    @Override
    public List<FullEinkaufslistenEintragDTO> getAllEintraege(String wgName) {


        List<FullEinkaufslistenEintragDTO> einkaufsListenListDTOS = new ArrayList<>();
        List<EinkaufslistenEintrag> einkaufslistenList = EinkaufslistenEintrag.findAll().list();
        for (EinkaufslistenEintrag eintrag : einkaufslistenList) {
            if (eintrag.getWg().getWgname().equals(wgName)) {
                einkaufsListenListDTOS.add(new FullEinkaufslistenEintragDTO(eintrag));

            }
        }
        //Damit die Elemente in der richtigen reihenfolge in der einkaufslisten view angezeigt werden
        einkaufsListenListDTOS.sort(new FullComparator());
        return einkaufsListenListDTOS;
    }

    @Override
    public Optional<FullEinkaufslistenEintragDTO> getEintragById(long einkaufslistenEintragId) {
        //Falls hier nichts gefunden wird
        EinkaufslistenEintrag einkaufslistenEintrag = EinkaufslistenEintrag.findById(einkaufslistenEintragId);

        if (einkaufslistenEintrag != null) {
            FullEinkaufslistenEintragDTO fullEinkaufslistenEintragDTO = new FullEinkaufslistenEintragDTO(einkaufslistenEintrag);
            return Optional.of(fullEinkaufslistenEintragDTO);
            //Wenn nicht gefunden wurde, return empty Opt
        } else {
            return Optional.empty();
        }
    }

    @Override
    public FullEinkaufslistenEintragDTO updateEinkauflistenElement(long einkaufslistenEintragId, int menge, String item, String besteller) {
        try {
            EinkaufslistenEintrag einkaufslistenEintrag = EinkaufslistenEintrag.findById(einkaufslistenEintragId);
            einkaufslistenEintrag.setMenge(menge);
            einkaufslistenEintrag.setItem(item);
            einkaufslistenEintrag.setMenge(menge);
            FullEinkaufslistenEintragDTO fullEinkaufslistenEintragDTO = new FullEinkaufslistenEintragDTO(einkaufslistenEintrag);
            EinkaufslistenEintrag.flush();
            return fullEinkaufslistenEintragDTO;
        } catch (ConstraintViolationException c) {
            throw new CustomConstraintViolationException(c.getMessage());
        }

    }

    @Override
    public long deleteEinkaufslistenEintragById(long einkaufslistenEintragId) {
        if (getEintragById(einkaufslistenEintragId) != null) {
            EinkaufslistenEintrag.deleteById(einkaufslistenEintragId);
            return einkaufslistenEintragId;
        }
        return 0;
    }

    @Override
    public FullEinkaufslistenEintragDTO createEintragbyView(String wgName, POSTEinkaufslistenEintragDTO postEinkaufslistenEintragDTO) {
        if (postEinkaufslistenEintragDTO != null) {
            return addEinkaufslistenElement(wgName, postEinkaufslistenEintragDTO.erledigt, postEinkaufslistenEintragDTO.menge, postEinkaufslistenEintragDTO.item, postEinkaufslistenEintragDTO.besteller);
        } else {
            System.out.println("Das postEinkaufslistenDTO ist NULL!");
        }
        return null;
    }

    @Override
    public FullEinkaufslistenEintragDTO addEinkaufslistenElement(String wgName, boolean erledigt, int menge, String item, String besteller) {
        try {
            EinkaufslistenEintrag einkaufslistenEintrag = new EinkaufslistenEintrag(erledigt, menge, item, besteller);
            einkaufslistenEintrag.setWg(WG.find("wgname", wgName).firstResult());
            einkaufslistenEintrag.persist();
            FullEinkaufslistenEintragDTO fullEinkaufslistenEintragDTO = new FullEinkaufslistenEintragDTO(einkaufslistenEintrag);
            EinkaufslistenEintrag.flush();
            return fullEinkaufslistenEintragDTO;
        } catch (ConstraintViolationException c) {
            throw new CustomConstraintViolationException(c.getMessage());
        }

    }

    @Override
    public PUTCheckboxDTO putCheckboxValue(PUTCheckboxDTO putCheckboxDTO) {
        try {
            EinkaufslistenEintrag einkaufslistenEintrag = EinkaufslistenEintrag.findById(putCheckboxDTO.einkauflistenEintragId);
            einkaufslistenEintrag.setErledigt(putCheckboxDTO.erledigt);
            return putCheckboxDTO;
        } catch (ConstraintViolationException c) {
            throw new CustomConstraintViolationException(c.getMessage());
        }

    }
}
