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
import utils.HttpHelper;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

@WebServlet("orders/form")
public class OrdersFormServlet extends HttpServlet {
    //TODO: Filter needs to work
    private static final OrdersRepository ORDERS_REPOSITORY = new OrdersRepository();

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestService requestService = RequestService.ofData(req);

        String body = requestService.getBody();

        OrderDto orderDto = ORDERS_REPOSITORY.save(new ObjectMapper().readValue(body, OrderDto.class));
//        String responseBody = HttpHelper.injectParam(orderDto.getContent(),
//                new AbstractMap.SimpleEntry<>("id", orderDto.getId()),
//                HttpHelper.ContentType.JSON);

//        resp.getWriter().println(responseBody);
        new ObjectMapper().writeValue(resp.getWriter(), orderDto);
    }
}
