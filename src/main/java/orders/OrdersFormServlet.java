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

@WebServlet("orders/form")
public class OrdersFormServlet extends HttpServlet {
    private static final OrdersRepository ORDERS_REPOSITORY = new OrdersRepository();

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestService requestService = RequestService.ofData(req);

        Map<String, Object> params = new HashMap<>();

        String contentType = req.getHeader("Content-Type");

        if (contentType.equals("application/x-www-form-urlencoded")) {
            Iterator<String> it = req.getParameterNames().asIterator();
            while (it.hasNext()) {
                String key = it.next();
                params.put(key, req.getParameterValues(key)[0]);
            }
        } else if (contentType.equals("application/json")){
            params = HttpHelper.readParams(requestService.getBody(), HttpHelper.ContentType.JSON);
        } else {
            throw new IllegalArgumentException();
        }

        HttpHelper.ContentType acceptType;
        Optional<String> acceptableFormat = Optional.ofNullable(req.getHeader("Accept"));

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
                    acceptType = contentType.equals("application/x-www-form-urlencoded") ? HttpHelper.ContentType.URLENCODED
                            : HttpHelper.ContentType.JSON;
                    break;
            }
        } else {
            acceptType = HttpHelper.ContentType.URLENCODED;
        }

        OrderDto orderDto = ORDERS_REPOSITORY.save(params);

        String returnData = HttpHelper.writeParamsAsString(params, acceptType);

        String responseBody = HttpHelper.injectParam(returnData,
                new AbstractMap.SimpleEntry<>("id", orderDto.getId()), acceptType);

        ResponseService responseService = ResponseService.ofData(resp);

        responseService.generateHeaders();
        responseService.send(responseBody);
    }
}
