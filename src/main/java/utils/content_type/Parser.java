package utils.content_type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private static final ContentTypeListener CONTENT_LISTENER = new ContentTypeListener();
    private static final Map<String, Method> READER_BY_TYPE = new HashMap<>();
    private static final Map<String, Method> WRITER_BY_TYPE = new HashMap<>();

    static {
        for (Method method : CONTENT_LISTENER.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ReaderType.class)) {
                READER_BY_TYPE.put(method.getAnnotation(ReaderType.class).type(), method);
            }

            if (method.isAnnotationPresent(WriterType.class)) {
                WRITER_BY_TYPE.put(method.getAnnotation(WriterType.class).type(), method);
            }
        }
    }

    public static Map<String, Object> readValueAsMap(String content, String type) {
        if (isReaderMethodExisting(type)) {
            Method readerMethod = READER_BY_TYPE.get(type);
            try {
                return (Map<String, Object>) readerMethod.invoke(CONTENT_LISTENER, content);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("No such content type reader");
    }

    public static String writeValueAsString(Map<String, Object> content, String type)  {
        if (isWriterMethodExisting(type)) {
            Method writerMethod = WRITER_BY_TYPE.get(type);
            try {
                return (String) writerMethod.invoke(CONTENT_LISTENER, content);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("No such content type writer");
    }

    private static boolean isWriterMethodExisting(String type) {
        return WRITER_BY_TYPE.containsKey(type);
    }

    private static boolean isReaderMethodExisting(String type) {
        return READER_BY_TYPE.containsKey(type);
    }
}
