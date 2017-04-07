package controller;

import general.JsonUtils;
import general.MyUtils;
import model.PrIS;
import model.persoon.AbsentieOpname;
import model.persoon.Student;
import server.Conversation;
import server.Handler;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.Date;

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
        try {
            if (conversation.getRequestedURI().startsWith("/absent_melden")) {
                absentMelden(conversation);
            } else if (conversation.getRequestedURI().startsWith("/present_melden")) {
                presentMelden(conversation);
            } else if (conversation.getRequestedURI().startsWith("/get_absenties_student")) {
                getAbsentiesStudent(conversation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            conversation.sendJSONMessage(JsonUtils.getErrorMessage(e.getMessage()));
        }
    }

    private void absentMelden(Conversation conversation) throws Exception {
        JsonObject input = (JsonObject) conversation.getRequestBodyAsJSON();
        String username = input.getString("username");
        Date absentVan = MyUtils.getDateFromMillis(input.getJsonNumber("absent_datum").longValue());
        Date absentTot = null;
        if (!input.getBoolean("undefined_date")) {
            absentTot = MyUtils.getDateFromMillis(input.getJsonNumber("present_datum").longValue());
        }

        Student student = informatieSysteem.getStudent(username);
        if (student == null) throw new Exception("Student niet gevonden!");

        student.setAbsent(new AbsentieOpname(absentVan, absentTot));

        conversation.sendJSONMessage(JsonUtils.getSuccessMessage("Je bent absent gemeld."));
    }

    private void presentMelden(Conversation conversation) throws Exception {
        JsonObject input = (JsonObject) conversation.getRequestBodyAsJSON();
        String username = input.getString("username");
        Date presentDate = MyUtils.getDateFromMillis(input.getJsonNumber("present_datum").longValue());

        Student student = informatieSysteem.getStudent(username);
        if (student == null) throw new Exception("Student niet gevonden!");

        student.setPresent(presentDate);

        conversation.sendJSONMessage(JsonUtils.getSuccessMessage("Je bent present gemeld."));
    }

    private void getAbsentiesStudent(Conversation conversation) throws Exception {
        JsonObject input = (JsonObject) conversation.getRequestBodyAsJSON();
        String username = input.getString("username");

        Student student = informatieSysteem.getStudent(username);
        if (student == null) throw new Exception("Student niet gevonden!");

        JsonArrayBuilder ret = Json.createArrayBuilder();
        for (AbsentieOpname opname: student.getAbsenties()) {
            ret.add(opname.serialize());
        }
        conversation.sendJSONMessage(ret.build().toString());
    }


}
