package http;

import jakarta.servlet.http.HttpServletRequest;
import utils.InputStreamReaderImpl;

import java.io.IOException;

public class RequestWrapper {
    private final HttpServletRequest servletRequest;
    public RequestWrapper(HttpServletRequest req) {
        this.servletRequest = req;
    }

    public String getBody() throws IOException {
        return InputStreamReaderImpl.readAll(servletRequest.getInputStream());
    }
}
