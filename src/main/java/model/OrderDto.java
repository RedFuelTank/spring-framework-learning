package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderDto {
    private Long id;
    private String orderNumber;
    private List<Item> orderRows;

    @Setter
    @Getter
    public static class Item {
        private String itemName;
        private int quantity;
        private int price;
    }
}
