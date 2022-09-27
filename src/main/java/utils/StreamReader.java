package utils;

import java.io.*;
import java.util.Scanner;
import java.util.StringJoiner;

public final class StreamReader {
    private StreamReader() {}

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
