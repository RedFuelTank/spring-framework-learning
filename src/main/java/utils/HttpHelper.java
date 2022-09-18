package utils;

import java.util.Map;

public class HttpHelper {
    public static Map<String, Object> readParams(String data) {
        return JsonParser.readJson(data);
    }

    public static String jsonFrom(Map<String, Object> params) {
        return JsonParser.jsonFrom(params);
    }
}
