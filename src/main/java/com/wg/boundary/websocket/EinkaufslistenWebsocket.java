package com.wg.boundary.websocket;

import com.wg.boundary.rest.acl.einkaufslistenEintrag.FullEinkaufslistenEintragDTO;
import io.vertx.core.json.JsonObject;


import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
/**
 * @author tcirksen
 * */
@ServerEndpoint("/einkaufsliste")
@ApplicationScoped
public class EinkaufslistenWebsocket {

    List<Session> sessions = new ArrayList<>();

    public void eintragCreate(FullEinkaufslistenEintragDTO fullEinkaufslistenEintragDTO){
        JsonObject typeHelper = new JsonObject();
        typeHelper.put("type", "eintrag_created");
        typeHelper.put("id", fullEinkaufslistenEintragDTO.einkaufslistenEintragId);
        typeHelper.put("erledigt", fullEinkaufslistenEintragDTO.erledigt);
        typeHelper.put("menge", fullEinkaufslistenEintragDTO.menge);
        typeHelper.put("item", fullEinkaufslistenEintragDTO.item);
        typeHelper.put("besteller", fullEinkaufslistenEintragDTO.besteller);
        broadcast(typeHelper.toString());
    }
    public void eintragDelete(long id){
        JsonObject typeHelper = new JsonObject();
        typeHelper.put("type", "eintrag_deleted");
        typeHelper.put("id", id);
        broadcast(typeHelper.toString());
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }


    private void broadcast(String json){
        for (Session session : sessions) {
            session.getAsyncRemote().sendText(json);
        }
    }
}
