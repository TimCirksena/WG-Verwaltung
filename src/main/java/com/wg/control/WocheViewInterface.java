package com.wg.control;

import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.core.SecurityContext;

/**
 * @author majaesch & tcirksen
 *
 * */
public interface WocheViewInterface {
    TemplateInstance getAktuelleWoche(SecurityContext securityContext);
    TemplateInstance getWocheMitJahrUndKW(SecurityContext securityContext, Integer jahr, Integer kw);
}
