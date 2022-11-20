package database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.OrderDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class OrdersRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public OrderDto save(OrderDto dto) {
        if (dto.getId() == null) {
            entityManager.persist(dto);
        } else {
            entityManager.merge(dto);
        }
        return dto;
    }

    @Transactional
    public Optional<OrderDto> getById(Long id) {
        OrderDto orderDto = entityManager.find(OrderDto.class, id);
        return Optional.ofNullable(orderDto);
    }

    @Transactional
    public List<OrderDto> getAll() {
        TypedQuery<OrderDto> query = entityManager.createQuery("select u from OrderDto u", OrderDto.class);
        return query.getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        OrderDto orderDto = entityManager.find(OrderDto.class, id);
        if (orderDto != null) {
            entityManager.remove(orderDto);
        }
    }
}
