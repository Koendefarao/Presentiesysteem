package general;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by EgorDm on 06-Apr-2017.
 */
public class MyUtils {

    public static Date getDateFromMillis(long date) throws ParseException {
        return new Date(date);
    }

    public static long getStringToMillis(Date date) {
        return date.getTime();
    }

    public static Calendar getNextWeekFrom(Calendar reference, Calendar from) {
        Calendar ret = (Calendar) from.clone();
        ret.set(Calendar.MINUTE, reference.get(Calendar.MINUTE));
        ret.set(Calendar.HOUR_OF_DAY, reference.get(Calendar.HOUR_OF_DAY));
        ret.set(Calendar.DAY_OF_WEEK, reference.get(Calendar.DAY_OF_WEEK));
        ret.add(Calendar.DAY_OF_WEEK, 7);

        return ret;
    }

    public static Date calendarToDate(Calendar in) {
        return new Date(in.getTimeInMillis());
    }

    public static Calendar dateToCalendar(Date in) {
        Calendar ret = Calendar.getInstance();
        ret.setTime(in);
        return ret;
    }
}
