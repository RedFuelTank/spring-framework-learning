package orders;

import database.OrdersRepository;
import model.OrderDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestServlet {
    private OrdersRepository ordersRepository;

    public TestServlet(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

//    @PostMapping()
//    protected OrderDto save(@RequestBody OrderDto orderDto) {
//        return ordersRepository.save(orderDto);
//    }
}
