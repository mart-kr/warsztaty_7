package pl.coderslab.warsztaty_7.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.repository.UserRepository;

import java.util.Optional;


@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserById(Long id) { return userRepository.findOne(id); }

}
