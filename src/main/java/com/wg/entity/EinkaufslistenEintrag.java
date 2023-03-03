package com.wg.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author tcirksen
 * */

@Entity
@Cacheable
public class EinkaufslistenEintrag extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "EinkaufslistenEintragSequence",
            sequenceName = "einkaufslistenEintrag_id_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EinkaufslistenEintragSequence")
    @Basic(optional = false)
    private long einkaufslisteEintragId;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "wgname")
    @NotNull
    private WG wg;
    @NotNull
    private boolean erledigt;
    @PositiveOrZero
    private int menge;
    @NotBlank
    private String item;
    @NotBlank
    private String besteller;

    public EinkaufslistenEintrag(boolean erledigt, int menge, String item, String besteller) {
        this.erledigt = erledigt;
        this.menge = menge;
        this.item = item;
        this.besteller = besteller;
    }

    public EinkaufslistenEintrag(){}

    public long getEinkaufslisteEintragId() {
        return einkaufslisteEintragId;
    }

    public void setEinkaufslisteEintragId(long einkaufslisteEintragId) {
        this.einkaufslisteEintragId = einkaufslisteEintragId;
    }

    public boolean isErledigt() {
        return erledigt;
    }

    public void setErledigt(boolean erledigt) {
        this.erledigt = erledigt;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBesteller() {
        return besteller;
    }

    public void setBesteller(String besteller) {
        this.besteller = besteller;
    }

    public WG getWg() {
        return wg;
    }

    public void setWg(WG wg) {
        this.wg = wg;
    }
}
