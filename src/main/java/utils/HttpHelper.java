package utils;

import java.util.Map;

public class HttpHelper {
    public static Map<String, Object> readParams(String data) {
        return JsonParser.readJson(data);
    }

    public static String jsonFrom(Map<String, Object> params) {
        return JsonParser.jsonFrom(params);
    }

    public static void main(String[] args) {
        class Parser {
            public static void test(String data) {
                Map<String, Object> params = readParams(data);
                String returnJsonBody = jsonFrom(params);
                Map<String, Object> returnParams = readParams(returnJsonBody);

            }
        }

        String jsonBody = "{\n" +
                "  \"firstName\": \"John\",\n" +
                "  \"lastName\": \"Smith\",\n" +
                "  \"age\": \"25\",\n" +
                "  \"address\": {\n" +
                "    \"streetAddress\": \"21 2nd Street\",\n" +
                "    \"city\": \"New York\",\n" +
                "    \"state\": \"NY\",\n" +
                "    \"postalCode\": \"10021\"\n" +
                "  },\n" +
                "  \"phoneNumber\": [\n" +
                "    {\n" +
                "      \"type\": \"home\",\n" +
                "      \"number\":    \"212 555-1239\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"fax\",\n" +
                "      \"number\": \"646 555-4567\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"gender\": [{\n" +
                "    \"type\": \"male\",\n" +
                "  }]\n" +
                "}";
        Parser.test(jsonBody);

        String littleJsonBody = "{\"key\": \"value\"}";
//        params = readParams(littleJsonBody);

        String littleJsonWithOneInnerObject = "{\"key\": {\"innerObjectKey\": \"innerObjectValue\"}}";


    }
}
