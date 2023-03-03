package com.wg.boundary.rest.acl.einkaufslistenEintrag;

import com.wg.entity.EinkaufslistenEintrag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author tcirksen
 * */
public class POSTEinkaufslistenEintragDTO
{
    @NotNull
    public boolean erledigt;
    @PositiveOrZero
    public Integer menge;
    @NotBlank
    public String item;
    @NotBlank
    public String besteller;

    public POSTEinkaufslistenEintragDTO() {
    }

    public POSTEinkaufslistenEintragDTO(boolean erledigt, int menge, String item, String besteller) {
        this.erledigt = erledigt;
        this.menge = menge;
        this.item = item;
        this.besteller = besteller;
    }
    public POSTEinkaufslistenEintragDTO(EinkaufslistenEintrag einkaufslistenEintrag){
        this.erledigt = einkaufslistenEintrag.isErledigt();
        this.menge = einkaufslistenEintrag.getMenge();
        this.item = einkaufslistenEintrag.getItem();
        this.besteller = einkaufslistenEintrag.getBesteller();
    }
}
