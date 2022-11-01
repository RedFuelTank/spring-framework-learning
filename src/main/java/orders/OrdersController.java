package orders;

import database.repository.OrdersRepository;
import handlers.ValidationErrors;
import jakarta.validation.Valid;
import model.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.management.AttributeNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    //TODO: Filter needs to work

    private OrdersRepository ordersRepository;

    public OrdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @PostMapping()
    protected OrderDto save(@RequestBody @Valid OrderDto orderDto) {
        return ordersRepository.save(orderDto);
    }

    @GetMapping("/{id}")
    protected OrderDto getById(@PathVariable Long id) {
        return ordersRepository.getById(id);
    }

    @GetMapping()
    protected List<OrderDto> getAll() {
        return ordersRepository.getAll();
    }

    @DeleteMapping
    protected void deleteById(@RequestParam Optional<Long> possibleId) throws AttributeNotFoundException {
        Long id = possibleId.orElseThrow(AttributeNotFoundException::new);

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
