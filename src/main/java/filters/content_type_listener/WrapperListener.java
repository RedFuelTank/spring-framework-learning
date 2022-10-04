package filters.content_type_listener;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class WrapperListener {
    private static final HttpContentTypeListener CONTENT_TYPE_LISTENER = new HttpContentTypeListener();
    private static final HashMap<String, Method> requestContentTypes = new HashMap<>();
    private static final HashMap<String, Method> responseContentTypes = new HashMap<>();

    static {
        for (Method method : CONTENT_TYPE_LISTENER.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestContentType.class)) {
                requestContentTypes.put(method.getAnnotation(RequestContentType.class).type(), method);
            }

            if (method.isAnnotationPresent(ResponseContentType.class)) {
                responseContentTypes.put(method.getAnnotation(ResponseContentType.class).type(), method);
            }

            System.out.println(requestContentTypes);
            System.out.println(responseContentTypes);
        }
    }

    public static HttpServletRequestWrapper getRequestWrapperFor(HttpServletRequest request) {
        Method method = requestContentTypes.get(request.getContentType());
        try {
            return (HttpServletRequestWrapper) method.invoke(CONTENT_TYPE_LISTENER, request);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpServletResponseWrapper getResponseWrapperFor(HttpServletResponse response) {
        Method method = responseContentTypes.get(response.getContentType());
        try {
            return (HttpServletResponseWrapper) method.invoke(CONTENT_TYPE_LISTENER, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
