package database.repository;

import database.ConnectionInfo;
import database.DataSourceProvider;
import database.SqlExecutor;
import model.OrderDto;
import utils.ApplicationProperties;
import utils.HttpHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class OrdersRepository {
    private static final ConnectionInfo CONNECTION_INFO = ConnectionInfo.ofData(ApplicationProperties.getDataBaseUrl(),
            ApplicationProperties.getDataBaseUser(), ApplicationProperties.getDataBasePassword());

    private static final DataSource DATA_SOURCE = DataSourceProvider.getDataSource(CONNECTION_INFO);

    public OrderDto save(OrderDto dto) {
        String query = String.format("INSERT INTO orders (orderNumber) VALUES ('%s') RETURNING *",
                dto.getOrderNumber());
        return SqlExecutor.executeQuery(query, OrderDto.class);
    }

    public OrderDto getById(String id) {
        String query = String.format("SELECT * FROM orders WHERE id = '%s'", id);
        return SqlExecutor.executeQuery(query, OrderDto.class);
    }

    public List<OrderDto> getAll() {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM orders")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OrderDto> list = new ArrayList<>();

            while (resultSet.next()) {
                OrderDto orderDto = new OrderDto();
                orderDto.setId(resultSet.getLong("id"));
                orderDto.setOrderNumber(resultSet.getString("orderNumber"));
                list.add(orderDto);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
