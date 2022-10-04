package orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.repository.OrdersRepository;
import http.RequestService;
import http.ResponseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderDto;
import utils.HttpHelper;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/api/orders")
public class OrdersServlet extends HttpServlet {
    //TODO: Filter needs to work

    private static final OrdersRepository ORDERS_REPOSITORY = new OrdersRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestService requestService = RequestService.ofData(req);

        String body = requestService.getBody();
        System.out.println(body);

        OrderDto orderDto = ORDERS_REPOSITORY.save(new ObjectMapper().readValue(body, Map.class));
        String responseBody = HttpHelper.injectParam(orderDto.getContent(),
                new AbstractMap.SimpleEntry<>("id", orderDto.getId()),
                HttpHelper.ContentType.JSON);

        resp.getWriter().println(responseBody);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> idParameter = Optional.ofNullable(req.getParameter("id"));
        ResponseService responseService = ResponseService.ofData(resp);
//        Optional<String> acceptableFormat = Optional.ofNullable(req.getHeader("Accept"));

//        HttpHelper.ContentType acceptType;
//        String contentType = req.getHeader("Content-Type");

        if (idParameter.isPresent()) {
//            if (acceptableFormat.isPresent()) {
//                switch (acceptableFormat.get()) {
//                    case "application/json": {
//                        acceptType = HttpHelper.ContentType.JSON;
//                        break;
//                    }
//
//                    case "application/x-www-form-urlencoded": {
//                        acceptType = HttpHelper.ContentType.URLENCODED;
//                        break;
//                    }
//                    default:
//                        acceptType = contentType.equals("application/x-www-form-urlencoded") ? HttpHelper.ContentType.URLENCODED
//                                : HttpHelper.ContentType.JSON;
//                        break;
//                }
//            } else {
//                acceptType = HttpHelper.ContentType.URLENCODED;
//            }
            OrderDto orderDto = ORDERS_REPOSITORY.getById(idParameter.get());

            String responseBody = HttpHelper.injectParam(orderDto.getContent(),
                    new AbstractMap.SimpleEntry<>("id", orderDto.getId()),
                    HttpHelper.ContentType.JSON);

            responseService.send(responseBody);
        } else {
            List<OrderDto> orderDtos = ORDERS_REPOSITORY.getAll();

//            List<String> list = orderDtos.stream()
//                    .map(e -> {
//                        try {
//                            return HttpHelper.injectParam(e.getContent(),
//                                    new AbstractMap.SimpleEntry<>("id", e.getId()),
//                                    HttpHelper.ContentType.JSON);
//                        } catch (JsonProcessingException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }).toList();

            new ObjectMapper().writeValue(resp.getWriter(), orderDtos);
        }
    }
}
