package orders;

import repository.OrdersRepository;
import model.OrderDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestServlet {
    private OrdersRepository ordersRepository;

    public TestServlet(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @PostMapping()
    protected OrderDto save(@RequestBody OrderDto orderDto) {
        return ordersRepository.save(orderDto);
    }

    @GetMapping
    public String test() {
        return "Successfully loaded";
    }
}
