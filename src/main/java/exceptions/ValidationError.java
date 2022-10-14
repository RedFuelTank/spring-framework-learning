package exceptions;

import lombok.Getter;

@Getter
public class ValidationError {
    private String code = "too_short_number";
}
