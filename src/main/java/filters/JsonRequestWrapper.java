package filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;

public class JsonRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public JsonRequestWrapper(HttpServletRequest request) {
        super(request);
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return super.getReader();
    }
}
