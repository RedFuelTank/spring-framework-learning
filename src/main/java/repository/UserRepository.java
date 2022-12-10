package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import model.UserDto;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager manager;
    @Transactional
    @Secured("ROLE_ADMIN")
    public List<UserDto> getAll() {
        TypedQuery<UserDto> query = manager.createQuery("select u from UserDto u", UserDto.class);
        return query.getResultList();
    }

    @Transactional
    @PreAuthorize("#username == authentication.name or hasAuthority('ROLE_ADMIN')")
    public UserDto getUserByUsername(String username) {
        TypedQuery<UserDto> query = manager.createQuery("select u from UserDto u where u.username = :username", UserDto.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

}
