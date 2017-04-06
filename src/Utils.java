import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by EgorDm on 06-Apr-2017.
 */
public class Utils {

    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    public static Date getDateFromString(String date) throws ParseException {
        return dateFormatter.parse(date);
    }

    public static String getStringFromDate(Date date) {
        return dateFormatter.format(date);
    }
}
