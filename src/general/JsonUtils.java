package general;

import javax.json.Json;

/**
 * Created by EgorDm on 06-Apr-2017.
 */
public class JsonUtils {

    private static final String successMessage = Json.createObjectBuilder().add("success", true).build().toString();

    public static String getErrorMessage(String message) {
        if(message == null) message = "No info about this error";
        return Json.createObjectBuilder()
                .add("success", false)
                .add("message", message)
                .build().toString();
    }

    public static String getSuccessMessage() {
        return successMessage;
    }
}
