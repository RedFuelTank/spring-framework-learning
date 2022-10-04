package filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.StreamReader;
import utils.content_type.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

public class UrlEncodedReader extends BufferedReader {
    private BufferedReader reader;

    public UrlEncodedReader(BufferedReader in) {
        super(in);
        reader = in;
    }

    @Override
    public Stream<String> lines() {
        try {
            Map<String, Object> params = Parser.readValueAsMap(StreamReader.readAll(reader),
                    "application/x-www-form-urlencoded");
            return Stream.of(new ObjectMapper().writeValueAsString(params));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
