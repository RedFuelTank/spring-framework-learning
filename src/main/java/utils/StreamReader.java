package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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

    public static String readAll(BufferedReader inputStream) throws IOException {
        return inputStream.lines().collect(Collectors.joining("\n"));
    }
}
