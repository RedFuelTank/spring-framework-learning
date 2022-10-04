package filters;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class UrlEncodedRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public UrlEncodedRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new UrlEncodedReader(new BufferedReader(
                new InputStreamReader(getCustomInputStream())));
    }

    private InputStream getCustomInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                getBodyFromParameters().getBytes()
        );
        return new InputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    private String getBodyFromParameters() {
        List<Map.Entry<String, String>> entries = new ArrayList<>();
        for (Iterator<String> iterator = super.getParameterNames().asIterator(); iterator.hasNext(); ) {
            String parameter = iterator.next();
            String value = super.getParameter(parameter);
            entries.add(new AbstractMap.SimpleEntry<>(parameter, value));
        }
        return entries.stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
    }
}
