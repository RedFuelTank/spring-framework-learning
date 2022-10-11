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
import java.util.List;
import java.util.Optional;

@WebServlet("/api/orders")
public class OrdersServlet extends HttpServlet {
    //TODO: Filter needs to work

    private static final OrdersRepository ORDERS_REPOSITORY = new OrdersRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestService requestService = RequestService.ofData(req);

        String body = requestService.getBody();

        OrderDto orderDto = ORDERS_REPOSITORY.save(new ObjectMapper().readValue(body, OrderDto.class));

        new ObjectMapper().writeValue(resp.getWriter(), orderDto);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> idParameter = Optional.ofNullable(req.getParameter("id"));

        if (idParameter.isPresent()) {
            OrderDto orderDto = ORDERS_REPOSITORY.getById(Long.valueOf(idParameter.get()));

            new ObjectMapper().writeValue(resp.getWriter(), orderDto);
        } else {
            List<OrderDto> orderDtos = ORDERS_REPOSITORY.getAll();

            new ObjectMapper().writeValue(resp.getWriter(), orderDtos);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> idParameter = Optional.ofNullable(req.getParameter("id"));

        idParameter.ifPresent(id -> ORDERS_REPOSITORY.deleteById(Long.valueOf(id)));
    }
}
