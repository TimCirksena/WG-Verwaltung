package com.wg.boundary.websocket;


import com.wg.boundary.rest.acl.kalender.POSTKEintragDTO;
import com.wg.boundary.rest.acl.kalender.ReturnKEintragDTO;
import io.vertx.core.json.JsonObject;
import net.bytebuddy.asm.Advice;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
/**
 * @author majaesch & tcirksen
 * */
@ServerEndpoint("/woche")
@ApplicationScoped
public class WochenWebsocket {
    List<Session> sessions = new ArrayList<>();

    public void wochenEintagCreate(ReturnKEintragDTO dto){
        Jsonb jsonb = JsonbBuilder.create();
        String resultJson = jsonb.toJson(dto);
        JsonObject typeHelper = new JsonObject(resultJson);
        typeHelper.put("type", "wochenEintrag_created");
        broadcast(typeHelper.toString());
    }

    public void wochenEintragUpdate(ReturnKEintragDTO dto){
        Jsonb jsonb = JsonbBuilder.create();
        String resultJson = jsonb.toJson(dto);
        JsonObject typeHelper = new JsonObject(resultJson);
        typeHelper.put("type", "wochenEintrag_updated");
        broadcast(typeHelper.toString());
    }
    public void wochenEintragDelete(Long id){
        JsonObject typeHelper = new JsonObject();
        typeHelper.put("type", "wochenEintrag_deleted");
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
