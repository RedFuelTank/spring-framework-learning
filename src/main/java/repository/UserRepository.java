package repository;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public String getData() {
        return "Successfully";
    }
}
