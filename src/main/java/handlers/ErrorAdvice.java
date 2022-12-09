package handlers;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import exceptions.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ControllerAdvice
public class ErrorAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrors validate(MethodArgumentNotValidException exception) {
        var fields = exception.getBindingResult().getFieldErrors();

        ValidationErrors validationErrors = new ValidationErrors();

        fields.forEach(validationErrors::addFieldError);

        return validationErrors;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String validate(NotFoundException exception) {
        return "";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String validate(Exception exception) {
        return "internal error!";
    }

}
