package com.wg.boundary.rest.acl.kalender;

import com.wg.entity.EintragsArt;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author majaesch
 *
 * */
public class POSTKEintragDTO {
    @NotBlank(message="name may not be blank")
    public String name;
    @NotBlank(message="datum may not be blank")
    public LocalDate datum;
    @NotBlank(message="startTime may not be blank")
    @JsonbDateFormat
    public LocalTime startTime;
    @NotBlank(message="endTime may not be blank")
    public LocalTime endTime;
    @NotBlank(message="eintragsArt may not be blank")
    public EintragsArt eintragsArt;
    @NotBlank(message="beteiligte may not be blank")
    public List<String> beteiligte;
    public String beschreibung;

    public int anzahlGaeste;
    public POSTKEintragDTO() {
    }
}
