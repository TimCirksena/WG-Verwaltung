package com.wg.gateway.repo;

import com.wg.boundary.rest.acl.kalender.POSTKEintragDTO;
import com.wg.boundary.rest.acl.wg.POSTWGDTO;
import com.wg.boundary.rest.acl.wg.PUTWGDTO;
import com.wg.boundary.rest.acl.wg.ReturnWGDTO;
import com.wg.boundary.rest.acl.wg.UpdateWGNameDTO;
import com.wg.boundary.rest.exceptionMappers.CustomConstraintViolationException;
import com.wg.boundary.rest.exceptionMappers.CustomPersistenceException;
import com.wg.entity.*;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author majaesch
 */
@ApplicationScoped
public class WGRepo implements WGCatalog {
    @Inject
    EinkaufslistenCatalog einkaufslistenCatalog;
    @Inject
    KalenderEintragCatalog kalenderEintragCatalog;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
        WG.deleteAll();
        addWG("admin", "admin", "admin");
        addWG("user", "user", "wg");
        addWG("user2", "user2", "wg");
        einkaufslistenCatalog.addEinkaufslistenElement("user", false, 1, "Kokosmilsch", "Maxi");
        einkaufslistenCatalog.addEinkaufslistenElement("user", true, 3, "Deo", "Tim");
        einkaufslistenCatalog.addEinkaufslistenElement("admin", true, 3, "Deo", "Tim");

        List<String> beteiligte = new ArrayList<>();
        beteiligte.add("Tim");
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.NOON;

        POSTKEintragDTO postkEintragDTO = new POSTKEintragDTO();

        postkEintragDTO.name = "Essen gehen";
        postkEintragDTO.eintragsArt = EintragsArt.KOCHEN;
        postkEintragDTO.beschreibung = "Wir kochen selbst";
        postkEintragDTO.beteiligte = beteiligte;
        postkEintragDTO.datum = localDate;
        postkEintragDTO.endTime = localTime.plusHours(2L);
        postkEintragDTO.startTime = localTime;
        kalenderEintragCatalog.postKalenderEintrag("user", postkEintragDTO);
    }

    public ReturnWGDTO addWG(String wgname, String password, String role) {
        try{
        WG wg = new WG();
        wg.setWgname(wgname);
        wg.setPassword(BcryptUtil.bcryptHash(password));
        wg.setRole(role);
        wg.persistAndFlush();
        return new ReturnWGDTO(wg);
    } catch (ConstraintViolationException c) {
        throw new CustomConstraintViolationException(c.getMessage());
    } catch (PersistenceException p){
        throw new CustomPersistenceException(p.getMessage());
    }

}

    /**
     * @return
     */
    @Override
    public List<ReturnWGDTO> getAllWG() {
        List<ReturnWGDTO> dtos = new ArrayList<>();
        List<WG> wgs = WG.findAll().list();
        for (WG wg : wgs) {
            dtos.add(new ReturnWGDTO(wg));
        }
        return dtos;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ReturnWGDTO getWGbyId(Long id) {
        return new ReturnWGDTO(WG.findById(id));
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public ReturnWGDTO editWG(PUTWGDTO dto) {
        try {
            Optional<WG> toEdit = WG.findByIdOptional(dto.wgId);
            if (toEdit.isPresent()) {
                WG wg = toEdit.get();
                wg.setWgname(dto.wgName);
                wg.setPassword(BcryptUtil.bcryptHash(dto.password));
                WG.flush();
                return new ReturnWGDTO(wg);
            }
            return new ReturnWGDTO();
        } catch (ConstraintViolationException c) {
            throw new CustomConstraintViolationException(c.getMessage());
        }

    }

    /**
     * @param dto
     * @return
     */
    @Override
    public ReturnWGDTO postWG(POSTWGDTO dto) {
        return addWG(dto.wgName, dto.password, "wg");
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean deleteWG(Long id) {
        return WG.deleteById(id);
    }

    @Override
    public boolean deleteWGbyName(String wgName) {
        WG wg = WG.find("wgname", wgName).firstResult();
        if (wg != null) {
            wg.delete();

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateName(UpdateWGNameDTO updateWGNameDTO, String oldWGName) {
        try {
            WG wg = WG.find("wgname", oldWGName).firstResult();
            if (wg != null) {
                wg.setWgname(updateWGNameDTO.wgName);
                return true;
            } else {
                return false;
            }
        } catch (ConstraintViolationException c) {
            throw new CustomConstraintViolationException(c.getMessage());
        }

    }


}
