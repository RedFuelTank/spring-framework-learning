package database.repository;

import database.ConnectionInfo;
import database.DataSourceProvider;
import model.OrderDto;
import utils.ApplicationProperties;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepository {
    private static final ConnectionInfo CONNECTION_INFO = ConnectionInfo.ofData(ApplicationProperties.getDataBaseUrl(),
            ApplicationProperties.getDataBaseUser(), ApplicationProperties.getDataBasePassword());

    private static final DataSource DATA_SOURCE = DataSourceProvider.getDataSource(CONNECTION_INFO);

    public OrderDto save(OrderDto dto) {

        String orderQuery = "INSERT INTO orders (orderNumber) VALUES (?) RETURNING *";

        String itemQuery = "INSERT INTO items(orderid, itemname, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
             PreparedStatement itemStatement = connection.prepareStatement(itemQuery)) {
            orderStatement.setString(1, dto.getOrderNumber());
            ResultSet set = orderStatement.executeQuery();

            if (set != null) {
                set.next();
                dto.setId(set.getLong("id"));

                connection.setAutoCommit(false);

                if (dto.getOrderRows() != null) {
                    for (OrderDto.Item item : dto.getOrderRows()) {
                        itemStatement.setLong(1,dto.getId());
                        itemStatement.setString(2, item.getItemName());
                        itemStatement.setInt(3, item.getQuantity());
                        itemStatement.setInt(4, item.getPrice());
                        itemStatement.addBatch();
                    }

                    itemStatement.executeBatch();
                    connection.commit();
                }

                return dto;
            }

            throw new IllegalArgumentException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public OrderDto getById(Long id) {
        String query = String.format("SELECT * FROM orders WHERE id = '%s'", id);

        String items = String.format("SELECT * FROM items WHERE orderid = '%s'", id);

        try (Connection connection = DATA_SOURCE.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(query, id));

            if (resultSet.next()) {
                OrderDto orderDto = new OrderDto();
                orderDto.setId(resultSet.getLong("id"));
                orderDto.setOrderNumber(resultSet.getString("orderNumber"));

                ResultSet itemsSet = statement.executeQuery(items);

                while (itemsSet.next()) {
                    OrderDto.Item item = new OrderDto.Item();
                    item.setItemName(itemsSet.getString("itemName"));
                    item.setQuantity(itemsSet.getInt("quantity"));
                    item.setPrice(itemsSet.getInt("price"));
                    if (orderDto.getOrderRows() == null) {
                        orderDto.setOrderRows(new ArrayList<>());
                    }
                    orderDto.getOrderRows().add(item);
                }
                return orderDto;
            }

            throw new IllegalArgumentException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<OrderDto> getAll() {
        try (Connection connection = DATA_SOURCE.getConnection();
             Statement orderStatement = connection.createStatement();
             Statement itemStatement = connection.createStatement()) {
            ResultSet resultSet = orderStatement.executeQuery("SELECT * FROM orders");
            List<OrderDto> list = new ArrayList<>();

            while (resultSet.next()) {
                OrderDto orderDto = new OrderDto();
                orderDto.setId(resultSet.getLong("id"));
                orderDto.setOrderNumber(resultSet.getString("orderNumber"));

                ResultSet items = itemStatement.executeQuery(String.format("SELECT * FROM items WHERE orderid = %s",
                        orderDto.getId()));
//                System.out.println(items);

                while (items.next()) {
                    OrderDto.Item item = new OrderDto.Item();
                    item.setItemName(items.getString("itemName"));
                    item.setQuantity(items.getInt("quantity"));
                    item.setPrice(items.getInt("price"));
                    if (orderDto.getOrderRows() == null) {
                        orderDto.setOrderRows(new ArrayList<>());
                    }
                    orderDto.getOrderRows().add(item);
                }

                list.add(orderDto);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        String query = "DELETE FROM orders WHERE id = %s";

        try (Connection connection = DATA_SOURCE.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(query, id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
