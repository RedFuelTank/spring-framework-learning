package filters;

import java.io.BufferedReader;
import java.util.stream.Stream;

public class JsonReader extends BufferedReader {

    public JsonReader(BufferedReader in) {
        super(in);
    }
}
