package orders;

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
import java.util.*;

@WebServlet("/api/orders")
public class OrdersServlet extends HttpServlet {
    private static final OrdersRepository ORDERS_REPOSITORY = new OrdersRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ResponseService responseService = ResponseService.of(resp);
        RequestService requestService = RequestService.of(req);


        String contentType = Optional.ofNullable(req.getHeader("Content-Type")).orElse("application/json");
        Map<String, Object> params = new HashMap<>();

        if (contentType.equals("application/x-www-form-urlencoded")) {
            for (Iterator<String> it = req.getParameterNames().asIterator(); it.hasNext(); ) {
                String key = it.next();
                params.put(key, req.getParameterValues(key)[0]);
            }
        } else if (contentType.equals("application/json")){
            params = HttpHelper.readParams(requestService.getBody(), HttpHelper.ContentType.JSON);
        } else {
            throw new IllegalArgumentException();
        }
        OrderDto orderDto = ORDERS_REPOSITORY.save(params);
        String responseBody = HttpHelper.injectParam(orderDto.getContent(),
                new AbstractMap.SimpleEntry<>("id", orderDto.getId()),
                HttpHelper.ContentType.JSON);

        responseService.generateHeaders();
        responseService.send(responseBody);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> idParameter = Optional.ofNullable(req.getParameter("id"));
        ResponseService responseService = ResponseService.of(resp);
        Optional<String> acceptableFormat = Optional.ofNullable(req.getHeader("Accept"));

        HttpHelper.ContentType acceptType;
        String contentType = req.getHeader("Content-Type");

        if (idParameter.isPresent()) {
            if (acceptableFormat.isPresent()) {
                switch (acceptableFormat.get()) {
                    case "application/json": {
                        acceptType = HttpHelper.ContentType.JSON;
                        break;
                    }

                    case "application/x-www-form-urlencoded": {
                        acceptType = HttpHelper.ContentType.URLENCODED;
                        break;
                    }
                    default:
                        acceptType = (contentType.equals("application/x-www-form-urlencoded") ? HttpHelper.ContentType.URLENCODED
                                : HttpHelper.ContentType.JSON);
                        break;
                }
            } else {
                acceptType = HttpHelper.ContentType.URLENCODED;
            }
            OrderDto orderDto = ORDERS_REPOSITORY.getById(idParameter.get());

            String responseBody = HttpHelper.injectParam(orderDto.getContent(),
                    new AbstractMap.SimpleEntry<>("id", orderDto.getId()),
                    acceptType);

            responseService.generateHeaders();
            responseService.send(responseBody);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
