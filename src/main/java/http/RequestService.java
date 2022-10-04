package http;

import jakarta.servlet.http.HttpServletRequest;
import utils.StreamReader;

import java.io.IOException;

public final class RequestService {
    private final HttpServletRequest servletRequest;
    private RequestService(HttpServletRequest req) {
        this.servletRequest = req;
    }

    public static RequestService ofData(HttpServletRequest request) {
        return new RequestService(request);
    }

    public String getBody() throws IOException {
        return StreamReader.readAll(servletRequest.getReader());
    }
}
