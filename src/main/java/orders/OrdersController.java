package orders;

import database.OrdersRepository;
import handlers.ValidationErrors;
import jakarta.validation.Valid;
import model.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private OrdersRepository ordersRepository;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class NotFoundException extends RuntimeException {
    }

    public OrdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @PostMapping()
    protected OrderDto save(@RequestBody @Valid OrderDto orderDto) {
        return ordersRepository.save(orderDto);
    }

    @GetMapping("/{id}")
    protected OrderDto getById(@PathVariable Long id) {
        return ordersRepository.getById(id)
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping()
    protected List<OrderDto> getAll() {
        return ordersRepository.getAll();
    }
    @DeleteMapping
    protected void deleteById(@RequestParam Optional<Long> possibleId) {
        Long id = possibleId.orElseThrow(NotFoundException::new);

        ordersRepository.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrors onException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        ValidationErrors validationErrors = new ValidationErrors();

        validationErrors.addAllFieldErrors(fieldErrors);
        return validationErrors;
    }
}
