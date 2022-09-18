package utils;

import java.io.*;
import java.util.StringJoiner;

public class InputStreamReaderImpl extends BufferedReader {
    public InputStreamReaderImpl(Reader in) {
        super(in);
    }

    public static String readAll(InputStream inputStream) throws IOException {
        StringJoiner joiner;

        try (InputStreamReaderImpl reader = new InputStreamReaderImpl(new InputStreamReader(inputStream))) {
            joiner = new StringJoiner("\n");
            String line;

            while ((line = reader.readLine()) != null) {
                joiner.add(line);
            }
        }
        return joiner.toString();
    }
}
