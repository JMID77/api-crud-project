package infrastructure.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import domain.model.User;
import domain.repository.UserRepository;
import infrastructure.persistence.entity.UserEntity;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Autowired
    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
     // public UserEntity findById(Long id) {
        return jpaUserRepository.findById(id)
            .map(entity -> new User(entity.getId(), entity.getName(), entity.getEmail()));
            // .orElse(null);
    }

    @Override
    public void save(User user) {
        UserEntity entity = new UserEntity(user.getId(), user.getName(), user.getEmail());
        jpaUserRepository.save(entity);
    }


}
