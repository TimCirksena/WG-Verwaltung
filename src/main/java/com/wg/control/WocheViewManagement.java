package com.wg.control;

import com.wg.boundary.rest.acl.kalender.ReturnKEintragDTO;
import com.wg.entity.KalenderEintragCatalog;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

/**
 * @author majaesch & tcirksen
 *
 * */
@ApplicationScoped
public class WocheViewManagement implements WocheViewInterface {
    @Inject
    Template woche_view;
    @Inject
    KalenderEintragCatalog kalenderEintragCatalog;

    @Override
    public TemplateInstance getAktuelleWoche(SecurityContext securityContext) {
        LocalDate aktuellesDatum = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int kw = aktuellesDatum.get(weekFields.weekOfWeekBasedYear());
        return getWocheMitJahrUndKW(securityContext, aktuellesDatum.getYear(),kw);
    }

    @Override
    public TemplateInstance getWocheMitJahrUndKW(SecurityContext securityContext, Integer jahr, Integer kw) {
        LocalDate montag = LocalDate.of(jahr, Month.JUNE, 1)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, kw)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sonntag = montag.plusDays(6);
        List<ReturnKEintragDTO> kalenderEintraegeWoche = kalenderEintragCatalog.getKalenderEintraegeWoche(securityContext.getUserPrincipal().getName(),montag);
        return woche_view.data("montag",montag,"sonntag",sonntag,"kw",kw,"kalenderEintraegeWoche",kalenderEintraegeWoche);
    }
}
