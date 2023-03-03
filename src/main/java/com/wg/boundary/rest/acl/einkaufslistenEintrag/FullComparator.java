package com.wg.boundary.rest.acl.einkaufslistenEintrag;

import java.util.Comparator;
/**
 * @author tcirksen
 * */
public class FullComparator implements Comparator<FullEinkaufslistenEintragDTO> {
    @Override
    public int compare(FullEinkaufslistenEintragDTO dto1, FullEinkaufslistenEintragDTO dto2) {
        return Long.compare(dto1.einkaufslistenEintragId, dto2.einkaufslistenEintragId);
    }
}
