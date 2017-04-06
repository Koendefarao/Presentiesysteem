package controller;

import model.PrIS;
import server.Conversation;
import server.Handler;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Created by Kamal on 6-4-2017.
 */
public class PresentieController implements Handler {

    private PrIS informatieSysteem;

    public PresentieController(PrIS informatieSysteem) {
        this.informatieSysteem = informatieSysteem;
    }

    @Override
    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith("/absent_melden_leerling")) {
            absentMeldenLeerling(conversation);
        }
    }

    public void absentMeldenLeerling(Conversation conversation) {
        JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
        PrIS.standaardDatumStringToCal(lJsonObjIn.getString("absentie"));

        JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
        lJsonObjectBuilder.add("test", "hello world");																	// en teruggekregen gebruikersrol als JSON-object...
        String lJsonOut = lJsonObjectBuilder.build().toString();

        conversation.sendJSONMessage(lJsonOut);
    }
}
