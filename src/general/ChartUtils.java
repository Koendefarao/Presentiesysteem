package general;

import model.persoon.Les;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by EgorDm on 08-Apr-2017.
 */
public class ChartUtils {

    private static final JsonArrayBuilder monthColumns = createColumns("Maand", "string", "Absenties", "number");
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

    public static JsonObjectBuilder chartAbsentiesByMonth(ArrayList<Les> lesAbsenties) {
        lesAbsenties.sort(monthComparator);

        JsonArrayBuilder rows = Json.createArrayBuilder();
        int monthTotal = 0;
        Calendar currMonth = null;
        for (Les absentie : lesAbsenties) {
            if (currMonth == null || absentie.getDatumStart().get(Calendar.MONTH) != currMonth.get(Calendar.MONTH)) {
                if (currMonth != null) {
                    rows.add(Json.createArrayBuilder()
                            .add(monthFormat.format(MyUtils.calendarToDate(currMonth)))
                            .add(monthTotal));
                }
                monthTotal = 0;
                currMonth = absentie.getDatumStart();
            }
            monthTotal++;
        }
        if (currMonth != null) {
            rows.add(Json.createArrayBuilder()
                    .add(monthFormat.format(MyUtils.calendarToDate(currMonth)))
                    .add(monthTotal));
        }
        return Json.createObjectBuilder()
                .add("columns", monthColumns)
                .add("rows", rows);
    }

    public static JsonArrayBuilder createColumns(String x, String xType, String y, String yType) {
        return Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("label", x).add("type", xType))
                .add(Json.createObjectBuilder().add("label", y).add("type", yType));
    }
}
