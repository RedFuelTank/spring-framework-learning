package utils;

import java.io.*;
import java.util.Scanner;
import java.util.StringJoiner;

public class InputStreamReaderImpl extends BufferedReader {
    public InputStreamReaderImpl(Reader in) {
        super(in);
    }

    public static String readAll(InputStream inputStream) throws IOException {
        StringJoiner joiner;

        try (Scanner reader = new Scanner(new InputStreamReader(inputStream))) {
            joiner = new StringJoiner("\n");

            while (reader.hasNext()) {
                joiner.add(reader.next());
            }
        }
        return joiner.toString();
    }
}
