package http;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class ResponseService {
    private HttpServletResponse servletResponse;
    private ResponseService(HttpServletResponse resp) {
        this.servletResponse = resp;
    }

    public static ResponseService of(HttpServletResponse response) {
        return new ResponseService(response);
    }

    public void generateHeaders() {
        servletResponse.setContentType("application/json");
    }


    public void send(String responseBody) throws IOException {
        servletResponse.getWriter().println(responseBody);

    }
}
