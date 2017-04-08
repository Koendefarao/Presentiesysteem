package controller;

import general.ChartUtils;
import general.JsonUtils;
import general.MyUtils;
import model.PrIS;
import model.persoon.Student;
import server.Conversation;
import server.Handler;

import javax.json.JsonObject;
import java.util.Date;

/**
 * Created by EgorDm on 08-Apr-2017.
 */
public class StatistiekController implements Handler {

    private PrIS informatieSysteem;

    public StatistiekController(PrIS informatieSysteem) {
        this.informatieSysteem = informatieSysteem;
    }

    @Override
    public void handle(Conversation conversation) {
        try {
            if (conversation.getRequestedURI().startsWith("/student_chart_by_month")) {
                studentChartByMonth(conversation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            conversation.sendJSONMessage(JsonUtils.getErrorMessage(e.getMessage()));
        }
    }

    public void studentChartByMonth(Conversation conversation) throws Exception {
        JsonObject input = (JsonObject) conversation.getRequestBodyAsJSON();
        String username = input.getString("username");
        Date from = MyUtils.getDateFromMillis(input.getJsonNumber("from").longValue());
        Date till = MyUtils.getDateFromMillis(input.getJsonNumber("till").longValue());

        Student student = informatieSysteem.getStudent(username);
        if (student == null) throw new Exception("Student niet gevonden!");

        conversation.sendJSONMessage(
                ChartUtils.chartAbsentiesByMonth(student.getGemisteLessen(informatieSysteem, from, till)).build().toString());
    }
}
