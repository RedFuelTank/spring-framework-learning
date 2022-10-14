package orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.repository.OrdersRepository;
import exceptions.ValidationError;
import handlers.ValidationErrors;
import http.RequestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderDto;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class OrdersServlet extends HttpServlet {
    //TODO: Filter needs to work

    private OrdersRepository ordersRepository;

    public OrdersServlet(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestService requestService = RequestService.ofData(req);

        String body = requestService.getBody();

        OrderDto orderDto = ordersRepository.save(new ObjectMapper().readValue(body, OrderDto.class));

        if (orderDto.getOrderNumber().length() < 2) {
            ValidationErrors errors = new ValidationErrors();
            errors.setErrors(List.of(new ValidationError()));
            resp.setStatus(400);

            new ObjectMapper().writeValue(resp.getWriter(), errors);
            return;
        }

        new ObjectMapper().writeValue(resp.getWriter(), orderDto);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> idParameter = Optional.ofNullable(req.getParameter("id"));

        if (idParameter.isPresent()) {
            OrderDto orderDto = ordersRepository.getById(Long.valueOf(idParameter.get()));

            new ObjectMapper().writeValue(resp.getWriter(), orderDto);
        } else {
            List<OrderDto> orderDtos = ordersRepository.getAll();

            new ObjectMapper().writeValue(resp.getWriter(), orderDtos);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> idParameter = Optional.ofNullable(req.getParameter("id"));

        idParameter.ifPresent(id -> ordersRepository.deleteById(Long.valueOf(id)));
    }
}
