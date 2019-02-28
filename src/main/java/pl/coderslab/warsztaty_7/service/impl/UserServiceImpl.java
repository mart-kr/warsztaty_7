package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.repository.UserRepository;
import pl.coderslab.warsztaty_7.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void editUser(User user) {
        userRepository.save(user);
    }

    public User findUserById(Long id) { return userRepository.findOne(id); }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username)
                .map(User::new)
                .orElseThrow(() -> new UsernameNotFoundException("There is no such user"));
    }
}
