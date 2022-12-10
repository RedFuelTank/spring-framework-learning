package controller;

import repository.OrdersRepository;
import model.OrderDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/form")
public class OrdersFormController {
    //TODO: Filter needs to work
    private OrdersRepository ordersRepository;

    public OrdersFormController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @PostMapping()
    protected OrderDto save(@RequestBody OrderDto orderDto) {
        return ordersRepository.save(orderDto);
    }
}
