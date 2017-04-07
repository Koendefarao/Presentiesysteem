package general;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 * Created by EgorDm on 06-Apr-2017.
 */
public class JsonUtils {

    public static String getErrorMessage(String message) {
        if(message == null) message = "No info about this error";
        return Json.createObjectBuilder()
                .add("success", false)
                .add("message", message)
                .build().toString();
    }

    public static String getSuccessMessage(String message) {
        if(message == null) message = "Actie geslaagd.";
        return Json.createObjectBuilder()
                .add("success", true)
                .add("message", message)
                .build().toString();
    }
}
