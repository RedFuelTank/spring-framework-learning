package database.repository;

import database.SqlExecutor;
import model.OrderDto;
import utils.HttpHelper;

import java.util.Map;

public class OrdersRepository {
    public OrderDto save(Map<String, Object> params)  {
        String query = String.format("INSERT INTO orders (content) VALUES ('%s') RETURNING *",
                HttpHelper.writeParamsAsString(params, HttpHelper.ContentType.JSON));
        return SqlExecutor.executeQuery(query, OrderDto.class);
    }

    public OrderDto getById(String id) {
        String query = String.format("SELECT * FROM orders WHERE id = '%s'", id);
        return SqlExecutor.executeQuery(query, OrderDto.class);
    }
}
