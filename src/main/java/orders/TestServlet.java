package orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.repository.OrdersRepository;
import http.RequestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderDto;

import java.io.IOException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private static final OrdersRepository ORDERS_REPOSITORY = new OrdersRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestService requestService = RequestService.ofData(req);

        String body = requestService.getBody();

        OrderDto orderDto = ORDERS_REPOSITORY.save(new ObjectMapper().readValue(body, OrderDto.class));
//        String responseBody = HttpHelper.injectParam(orderDto.getContent(),
//                new AbstractMap.SimpleEntry<>("id", orderDto.getId()),
//                HttpHelper.ContentType.JSON);

        new ObjectMapper().writeValue(resp.getWriter(), orderDto);
    }
}
