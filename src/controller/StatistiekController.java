package controller;

import general.ChartUtils;
import general.JsonUtils;
import general.MyUtils;
import model.PrIS;
import model.klas.Klas;
import model.persoon.Student;
import server.Conversation;
import server.Handler;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
                studentChartByMonthPercentage(conversation);
                //studentChartByMonth(conversation);
            }
            if (conversation.getRequestedURI().startsWith("/docent_chart_klas_by_month")) {
                //docentChartKlasByMonth(conversation);
                docentChartKlasByMonthPercentage(conversation);
            }
            if (conversation.getRequestedURI().startsWith("/get_klassen")) {
                getKlassen(conversation);
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
                ChartUtils.chartAbsentiesByMonth(
                        student.getGemisteLessen(informatieSysteem, from, till),
                        MyUtils.dateToCalendar(from),
                        MyUtils.dateToCalendar(till)).build().toString());
    }

    public void studentChartByMonthPercentage(Conversation conversation) throws Exception {
        JsonObject input = (JsonObject) conversation.getRequestBodyAsJSON();
        String username = input.getString("username");
        Date from = MyUtils.getDateFromMillis(input.getJsonNumber("from").longValue());
        Date till = MyUtils.getDateFromMillis(input.getJsonNumber("till").longValue());

        Student student = informatieSysteem.getStudent(username);
        if (student == null) throw new Exception("Student niet gevonden!");

        //System.out.println(student.getGemisteLessen(informatieSysteem, from, till).size());
        //System.out.println(student.getLessen(informatieSysteem, from, till).size());
        conversation.sendJSONMessage(
                ChartUtils.chartAbsentiesByMonthInPrecent(
                        student.getGemisteLessen(informatieSysteem, from, till),
                        student.getLessen(informatieSysteem, from, till),
                        MyUtils.dateToCalendar(from),
                        MyUtils.dateToCalendar(till)).build().toString());
    }

    public void getKlassen(Conversation conversation) {
        JsonArrayBuilder ret  = Json.createArrayBuilder();
        for(Klas klas : informatieSysteem.getKlassen()) {
            ret.add(klas.getKlasCode());
        }
        conversation.sendJSONMessage(ret.build().toString());
    }

    public void docentChartKlasByMonth(Conversation conversation) throws Exception {
        JsonObject input = (JsonObject) conversation.getRequestBodyAsJSON();
        String klasCode = input.getString("klas");
        Date from = MyUtils.getDateFromMillis(input.getJsonNumber("from").longValue());
        Date till = MyUtils.getDateFromMillis(input.getJsonNumber("till").longValue());

        Klas klas = informatieSysteem.getKlas(klasCode);
        if (klas == null) throw new Exception("Klas niet gevonden!");

        conversation.sendJSONMessage(
                ChartUtils.chartAbsentiesByMonth(
                        klas.getGemisteLessen(informatieSysteem, from, till),
                        MyUtils.dateToCalendar(from),
                        MyUtils.dateToCalendar(till)).build().toString());
    }


    public void docentChartKlasByMonthPercentage(Conversation conversation) throws Exception {
        JsonObject input = (JsonObject) conversation.getRequestBodyAsJSON();
        String klasCode = input.getString("klas");
        Date from = MyUtils.getDateFromMillis(input.getJsonNumber("from").longValue());
        Date till = MyUtils.getDateFromMillis(input.getJsonNumber("till").longValue());

        Klas klas = informatieSysteem.getKlas(klasCode);
        if (klas == null) throw new Exception("Klas niet gevonden!");

        conversation.sendJSONMessage(
                ChartUtils.chartAbsentiesByMonthInPrecent(
                        klas.getGemisteLessen(informatieSysteem, from, till),
                        klas.getLessen(informatieSysteem, from, till),
                        MyUtils.dateToCalendar(from),
                        MyUtils.dateToCalendar(till)).build().toString());
    }
}
