package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import utils.content_type.Parser;

import java.util.Map;

public class HttpHelper {
    public static Map<String, Object> readParams(String data, ContentType type) {
        return Parser.readValueAsMap(data, type.toString());
    }
    public static String injectParam(String body, Map.Entry<String, Object> entry, ContentType type) throws JsonProcessingException {
        Map<String, Object> params = HttpHelper.readParams(body, type);
        params.put(entry.getKey(), entry.getValue());
        return HttpHelper.writeParamsAsString(params, type);
    }

    public static String writeParamsAsString(Map<String, Object> params, ContentType type) {
        return Parser.writeValueAsString(params, type.toString());
    }
    public enum ContentType {
        JSON("application/json"),
        URLENCODED("application/x-www-form-urlencoded");

        private String s;

        ContentType(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return s;
        }
    }
}
