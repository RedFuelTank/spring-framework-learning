package orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.repository.OrdersRepository;
import http.RequestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderDto;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class TestServlet extends HttpServlet {
    private OrdersRepository ordersRepository;

    public TestServlet(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestService requestService = RequestService.ofData(req);

        String body = requestService.getBody();

        OrderDto orderDto = ordersRepository.save(new ObjectMapper().readValue(body, OrderDto.class));

        new ObjectMapper().writeValue(resp.getWriter(), orderDto);
    }
}
