package com.wg.entity;

import com.wg.boundary.rest.acl.kalender.POSTKEintragDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 * @author majaesch, tcirksen
 * */
@Entity
public class KalenderEintrag extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "KalenderEintragSequence",
            sequenceName = "kalenderEintrag_id_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KalenderEintragSequence")
    @Basic(optional = false)
    private long kalenderEintragId;
    @ManyToOne
    @JoinColumn(name = "wgname")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private WG wg;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate datum;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private EintragsArt eintragsArt;
    @NotNull
    private int anzahlGaeste;
    @ElementCollection
    @JoinColumn(name = "kalendereintragid")            // name of the @Id column of this entity
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<String> beteiligte;
    private String beschreibung;

    public KalenderEintrag() {
    }

    public KalenderEintrag(POSTKEintragDTO dto) {
        this.name = dto.name;
        this.datum = dto.datum;
        this.startTime = dto.startTime;
        this.endTime = dto.endTime;
        this.eintragsArt = dto.eintragsArt;
        this.beteiligte = new ArrayList<>(dto.beteiligte);
        this.beschreibung = dto.beschreibung;
        this.anzahlGaeste = dto.anzahlGaeste;
    }

    public WG getWg() {
        return wg;
    }

    public void setWg(WG wg) {
        this.wg = wg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public EintragsArt getEintragsArt() {
        return eintragsArt;
    }

    public void setEintragsArt(EintragsArt eintragsArt) {
        this.eintragsArt = eintragsArt;
    }

    public List<String> getBeteiligte() {
        return beteiligte;
    }

    public void setBeteiligte(List<String> beteiligte) {
        this.beteiligte = beteiligte;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public long getKalenderEintragId() {
        return kalenderEintragId;
    }

    public int getAnzahlGaeste() {
        return anzahlGaeste;
    }

    public void setAnzahlGaeste(int anzahlGaeste) {
        this.anzahlGaeste = anzahlGaeste;
    }

}
