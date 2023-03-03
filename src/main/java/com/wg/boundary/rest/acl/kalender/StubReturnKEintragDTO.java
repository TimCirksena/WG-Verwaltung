package com.wg.boundary.rest.acl.kalender;

import com.wg.entity.EintragsArt;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author majaesch
 *
 * */
public class StubReturnKEintragDTO {


    public String name;

    public LocalDate datum;

    public LocalTime startTime;

    public LocalTime endTime;

    public EintragsArt eintragsArt;

    public StubReturnKEintragDTO() {
    }
}
