package orders;

import repository.OrdersRepository;
import exceptions.NotFoundException;
import jakarta.validation.Valid;
import model.OrderDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private OrdersRepository ordersRepository;

    public OrdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @PostMapping
    protected OrderDto save(@RequestBody @Valid OrderDto orderDto) {
        return ordersRepository.save(orderDto);
    }

    @GetMapping("/{id}")
    protected OrderDto getById(@PathVariable Long id) {
        return ordersRepository.getById(id)
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping
    protected List<OrderDto> getAll() {
        return ordersRepository.getAll();
    }
    @DeleteMapping
    protected void deleteById(@RequestParam Optional<Long> possibleId) {
        Long id = possibleId.orElseThrow(NotFoundException::new);

        ordersRepository.deleteById(id);
    }
}
