package filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.content_type.Parser;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class UrlEncodedWriter extends PrintWriter {

    public UrlEncodedWriter(Writer out) {
        super(out);
    }

    @Override
    public void println(String s) {
        try {
            Map<String, Object> params = new ObjectMapper().readValue(s, Map.class);
            super.println(Parser.writeValueAsString(params, "application/x-www-form-urlencoded"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
