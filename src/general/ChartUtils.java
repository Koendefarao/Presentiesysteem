package general;

import model.persoon.Les;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by EgorDm on 08-Apr-2017.
 */
public class ChartUtils {

    private static final SimpleDateFormat monthFormat = new SimpleDateFormat("MMM yy");
    private static final Comparator<Les> monthComparator = (o1, o2) -> {
        int month1 = o1.getDatumStart().get(Calendar.MONTH);
        int month2 = o2.getDatumStart().get(Calendar.MONTH);

        if (month1 < month2)
            return -1;
        else if (month1 == month2)
            return o1.getDatumStart().get(Calendar.DAY_OF_MONTH) - o2.getDatumStart().get(Calendar.DAY_OF_MONTH);

        else return 1;
    };

    public static JsonObjectBuilder chartAbsentiesByMonth(ArrayList<Les> lesAbsenties, Calendar from, Calendar till) {
        lesAbsenties.sort(monthComparator);

        JsonArrayBuilder rows = Json.createArrayBuilder();
        int monthTotal = 0;
        Calendar currMonth = (Calendar) from.clone();
        while (currMonth.get(Calendar.MONTH) <= till.get(Calendar.MONTH) || currMonth.before(till)) {
            for (Les absentie : lesAbsenties) {
                if (absentie.getDatumStart().get(Calendar.MONTH) == currMonth.get(Calendar.MONTH)
                        && absentie.getDatumStart().get(Calendar.YEAR) == currMonth.get(Calendar.YEAR)) {
                    monthTotal++;
                }
            }
            rows.add(Json.createArrayBuilder()
                    .add(monthFormat.format(MyUtils.calendarToDate(currMonth)))
                    .add(monthTotal));
            currMonth.add(Calendar.MONTH, 1);
            monthTotal = 0;
        }
        return Json.createObjectBuilder()
                .add("columns", createColumns("Maand", "string", "Absenties", "number"))
                .add("rows", rows);
    }

    public static JsonObjectBuilder chartAbsentiesByMonthInPrecent(ArrayList<Les> lesAbsenties, ArrayList<Les> lesTotaal, Calendar from, Calendar till) {
        lesAbsenties.sort(monthComparator);
        JsonArrayBuilder rows = Json.createArrayBuilder();
        int monthPresenties = 0;
        int monthAbsenties = 0;
        Calendar currMonth = (Calendar) from.clone();
        while (currMonth.get(Calendar.MONTH) <= till.get(Calendar.MONTH) || currMonth.before(till)) {
            for (Les absentie : lesAbsenties) {
                if (absentie.getDatumStart().get(Calendar.MONTH) == currMonth.get(Calendar.MONTH)
                        && absentie.getDatumStart().get(Calendar.YEAR) == currMonth.get(Calendar.YEAR)) {
                    monthAbsenties++;
                }
            }
            for (Les les : lesTotaal) {
                if (les.getDatumStart().get(Calendar.MONTH) == currMonth.get(Calendar.MONTH)
                        && les.getDatumStart().get(Calendar.YEAR) == currMonth.get(Calendar.YEAR)) {
                    monthPresenties++;
                }
            }
            double ratio = 0;
            if(monthPresenties > 0) {
                ratio = (double)(monthAbsenties)/ (double) monthPresenties;
            }
            rows.add(Json.createArrayBuilder()
                    .add(monthFormat.format(MyUtils.calendarToDate(currMonth)))
                    .add(ratio * 100));
            currMonth.add(Calendar.MONTH, 1);
            monthPresenties = monthAbsenties = 0;
        }
        return Json.createObjectBuilder()
                .add("columns", createColumns("Maand", "string", "Absenties in %", "number"))
                .add("rows", rows);
    }

    public static JsonArrayBuilder createColumns(String x, String xType, String y, String yType) {
        return Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("label", x).add("type", xType))
                .add(Json.createObjectBuilder().add("label", y).add("type", yType));
    }
}
