package utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonParser {
    public static final String KEY_GROUP = "key";
    public static final String STRING_GROUP = "string";
    public static final String NUMBER_GROUP = "number";
    public static final String OBJECT_GROUP = "object";
    public static final String LIST_GROUP = "list";
    public static final String REGEX_DELIMETER_LIST_OBJECTS = "(?<=}),";
    private static final Pattern JSON_PATTERN = Pattern.compile(
            "[\"'](?<key>[\\w\\d]+)[\"']:\\s*(?:[\"']+(?<string>(?s).*?)[\"']+|(?<object>[{]+(?s).*?[}]+)|\\[(?<list>(?s).*?)]|(?<number>[\\w\\s-]*))");

    private JsonParser() {}

    public static Map<String, Object> readJson(String body) {
        Map<String, Object> params = new HashMap<>();
        readJsonRecursively(body, params);
        return params;
    }

    private static void readJsonRecursively(String body, Map<String, Object> params) {
        Matcher matcher = JSON_PATTERN.matcher(body);
        while (matcher.find()) {
            if (isListGroup(matcher)) {
                List<Map<String, Object>> listInnerParams = new ArrayList<>();
                for (String param : matcher.group(LIST_GROUP).split(REGEX_DELIMETER_LIST_OBJECTS)) {
                    Map<String, Object> innerParams = new HashMap<>();
                    readJsonRecursively(param, innerParams);
                    listInnerParams.add(innerParams);
                }
                params.put(matcher.group(KEY_GROUP), listInnerParams);
            } else if (isObjectGroup(matcher)) {
                Map<String, Object> innerParams = new HashMap<>();
                readJsonRecursively(matcher.group(OBJECT_GROUP), innerParams);
                params.put(matcher.group(KEY_GROUP), innerParams);
            } else if (isString(matcher)) {
                params.put(matcher.group(KEY_GROUP), matcher.group(STRING_GROUP));
            } else {
                params.put(matcher.group(KEY_GROUP), matcher.group(NUMBER_GROUP));
            }
        }
    }

    private static boolean isString(Matcher matcher) {
        return matcher.group(STRING_GROUP) != null;
    }

    private static boolean isObjectGroup(Matcher matcher) {
        return matcher.group(OBJECT_GROUP) != null;
    }

    private static boolean isListGroup(Matcher matcher) {
        return matcher.group(LIST_GROUP) != null;
    }

    public static String jsonFrom(Map<String, Object> params) {
        StringJoiner joiner = new StringJoiner(", ");

        for (Map.Entry<String, Object> e : params.entrySet()) {
            if (e.getValue() instanceof Map<?, ?>) {
                joiner.add(String.format("\"%s\": %s", e.getKey(), jsonFrom((Map<String, Object>) e.getValue())));
            } else if (e.getValue() instanceof List<?>) {
                StringJoiner listJoiner = new StringJoiner(", ");
                for (Map<String, Object> object : (List<Map<String, Object>>) e.getValue()) {
                    listJoiner.add(jsonFrom(object));
                }
                joiner.add(String.format("\"%s\": [%s]", e.getKey(), listJoiner));
            } else {
                joiner.add(String.format("\"%s\": \"%s\"", e.getKey(), e.getValue()));
            }
        }
        return String.format("{%s}", joiner);
    }
}
