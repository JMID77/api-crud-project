package application.service;

import domain.model.User;
import domain.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return this.userRepository.findById(id);
        //return userRepository.findById(id).orElse(null);
    }

    public void createUser(String name, String email) {
        if (name == null || email == null || name.isBlank() || email.isBlank()) {
            throw new IllegalArgumentException("Name and email cannot be empty");
        }
        
        User user = new User(null, name, email);

        this.userRepository.save(user);
    }

}
