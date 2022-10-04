package filters;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;

public class UrlEncodedResponseWrapper extends HttpServletResponseWrapper {
    private PrintWriter urlEncodedWriter;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response the {@link HttpServletResponse} to be wrapped.
     * @throws IllegalArgumentException if the response is null
     */
    public UrlEncodedResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        urlEncodedWriter = new UrlEncodedWriter(response.getWriter());
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return urlEncodedWriter;
    }
}
