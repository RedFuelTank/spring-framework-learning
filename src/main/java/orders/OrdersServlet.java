package orders;

import http.RequestWrapper;
import http.ResponseWrapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.HttpHelper;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/orders")
public class OrdersServlet extends HttpServlet {
    private long id;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseWrapper response = new ResponseWrapper(resp);
        RequestWrapper request = new RequestWrapper(req);

        String requestBody = request.getBody();
        String responseBody = injectIdentifierToBody(requestBody);

        response.generateHeaders();
        response.send(responseBody);
    }

    private String injectIdentifierToBody(String body) {
        Map<String, Object> params = HttpHelper.readParams(body);
        params.put("id", generateIdentifier());
        return HttpHelper.jsonFrom(params);
    }

    private long generateIdentifier() {
        return ++id;
    }
}
