package utils.content_type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ContentTypeListener {
    @ReaderType(type = "application/json")
    public Map<String, Object> readJson(String content) throws JsonProcessingException {
        return new ObjectMapper().readValue(content, Map.class);
    }

    @WriterType(type = "application/json")
    public String writeJson(Map<String, Object> data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }

    @ReaderType(type = "application/x-www-form-urlencoded")
    public Map<String, Object> readUrlEncoded(String content) {
        String[] params = content.split("&");
        return Arrays.stream(params)
                .map(param -> param.split("="))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }

    @WriterType(type = "application/x-www-form-urlencoded")
    public String writeUrlEncoded(Map<String, Object> data) {
        return data.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
    }
}
