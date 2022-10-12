package database.repository;

import model.OrderDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class OrdersRepository {
    // TODO: Validation
    private final JdbcTemplate jdbcTemplate;

    public OrdersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OrderDto save(OrderDto dto) {
        MapSqlParameterSource orderSqlParameters = new MapSqlParameterSource();
        orderSqlParameters.addValue("ordernumber", dto.getOrderNumber());

        Long id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id").executeAndReturnKey(orderSqlParameters).longValue();

        if (dto.getOrderRows() != null) {
            var items = dto.getOrderRows().stream()
                    .map(item -> {
                        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                        parameterSource.addValue("itemname", item.getItemName());
                        parameterSource.addValue("price", item.getPrice());
                        parameterSource.addValue("quantity", item.getQuantity());
                        parameterSource.addValue("orderid", id);
                        return parameterSource;
                    }).toArray(SqlParameterSource[]::new);

            new SimpleJdbcInsert(jdbcTemplate).withTableName("items").usingGeneratedKeyColumns("id").executeBatch(items);
        }

        dto.setId(id);

        return dto;
    }

    public OrderDto getById(Long id) {
        String orderById = "SELECT orders.*, i.id as itemid, i.itemname, i.quantity, i.price " +
                "FROM orders LEFT JOIN items i on orders.id = i.orderid WHERE orders.id = ?";

        return jdbcTemplate.query(orderById, new OrderDto.OrdersResultSetExtractor(), id)
                .get(0);
    }

    public List<OrderDto> getAll() {
        String orders = "SELECT orders.*, i.id as itemid, i.itemname, i.quantity, i.price " +
                "FROM orders LEFT JOIN items i on orders.id = i.orderid";

        return jdbcTemplate.query(orders, new OrderDto.OrdersResultSetExtractor());
    }

    public void deleteById(Long id) {
        String query = "DELETE FROM orders WHERE id = ?";

        jdbcTemplate.update(query, id);

    }
}
