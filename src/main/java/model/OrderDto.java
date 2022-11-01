package model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class OrderDto {
    private Long id;
    @NotNull
    private String orderNumber;
    @Valid
    private List<Item> orderRows;

    @Setter
    @Getter
    public static class Item {
        private String itemName;

        @NotNull
        @Min(1)
        private int quantity;

        @NotNull
        @Min(1)
        private int price;
    }

    public static class OrdersResultSetExtractor implements ResultSetExtractor<List<OrderDto>>{
        @Override
        public List<OrderDto> extractData(ResultSet rs) throws SQLException {
            Map<Long, OrderDto> orderDtoById = new HashMap<>();

            while (rs.next()) {
                Long id = rs.getLong("id");
                OrderDto orderDto = orderDtoById.get(id);

                if (orderDto == null) {
                    orderDto = new OrderDto();
                    orderDto.setId(id);
                    orderDto.setOrderRows(new ArrayList<>());
                    orderDto.setOrderNumber(rs.getString("ordernumber"));
                    orderDtoById.put(id, orderDto);
                }

                long itemId = rs.getLong("itemid");
                if (itemId != 0) {
                    Item item = new Item();
                    item.setQuantity(rs.getInt("quantity"));
                    item.setItemName(rs.getString("itemname"));
                    item.setPrice(rs.getInt("price"));
                    orderDto.getOrderRows().add(item);
                }
            }
            return orderDtoById.values().stream().toList();
        }
    }
}
