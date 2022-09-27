package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileReader {
    public static String readContentFromPath(String path) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {

            if (isExist(is)) {
                return StreamReader.readAll(is);
            }

            throw new FileNotFoundException();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isExist(InputStream is) {
        return is != null;
    }
}
