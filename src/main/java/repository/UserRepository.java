package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import model.UserDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager manager;
    @Transactional
    public List<UserDto> getAll() {
        TypedQuery<UserDto> query = manager.createQuery("select u from UserDto u", UserDto.class);
        return query.getResultList();
    }
}
