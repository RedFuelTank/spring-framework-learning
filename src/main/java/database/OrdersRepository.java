package database.repository;

import jakarta.persistence.*;
import model.OrderDto;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrdersRepository extends CrudRepository<OrderDto, Long> {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Transactional
//    public OrderDto save(OrderDto dto) {
//        if (dto.getId() == null) {
//            entityManager.persist(dto);
//        } else {
//            entityManager.merge(dto);
//        }
//        return dto;
//    }

//    public OrderDto getById(Long id) {
//        String orderById = "SELECT orders.*, i.id as itemid, i.itemname, i.quantity, i.price " +
//                "FROM orders LEFT JOIN items i on orders.id = i.orderid WHERE orders.id = ?";
//
//        return jdbcTemplate.query(orderById, new OrderDto.OrdersResultSetExtractor(), id)
//                .get(0);
//    }
//
//    @Transactional(readOnly = true)
//    public List<OrderDto> getAll() {
//        TypedQuery<OrderDto> query = entityManager.createQuery("select u from OrderDto u WHERE true", OrderDto.class);
//        return query.getResultList();
//    }
//
//    public void deleteById(Long id) {
//        String query = "DELETE FROM orders WHERE id = ?";
//
//        jdbcTemplate.update(query, id);
//
//    }
}
