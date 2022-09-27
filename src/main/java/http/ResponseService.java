package http;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class ResponseService {
    private final HttpServletResponse servletResponse;
    private ResponseService(HttpServletResponse resp) {
        this.servletResponse = resp;
    }

    public static ResponseService ofData(HttpServletResponse response) {
        return new ResponseService(response);
    }

    public void generateHeaders() {
        servletResponse.setContentType("application/json");
    }


    public void send(String responseBody) throws IOException {
        servletResponse.getWriter().println(responseBody);

    }
}
