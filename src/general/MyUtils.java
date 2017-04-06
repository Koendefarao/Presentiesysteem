package general;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
