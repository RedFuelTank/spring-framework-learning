package filters;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class JsonResponseWrapper extends HttpServletResponseWrapper {
    private PrintWriter jsonWriter;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response the {@link HttpServletResponse} to be wrapped.
     * @throws IllegalArgumentException if the response is null
     */
    public JsonResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        jsonWriter = new JsonWriter(response.getWriter());
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return jsonWriter;
    }

    @Override
    public String toString() {
        return jsonWriter.toString();
    }
}
