package http;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseWrapper {
    private HttpServletResponse servletResponse;
    public ResponseWrapper(HttpServletResponse resp) {
        this.servletResponse = resp;
    }

    public void generateHeaders() {
        servletResponse.setContentType("application/json");
    }


    public void send(String responseBody) throws IOException {
        servletResponse.getWriter().println(responseBody);

    }
}
