package domain.repository;

import domain.model.User;

import java.util.Optional;


public interface UserRepository {
    Optional<User> findById(Long id);
    // Optional<User> findById(Long id);
    void save(User user);
}
