package practice.practice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import practice.practice.customExceptions.UserAlreadyExistsException;
import practice.practice.objects.ApplicationUserRole;
import practice.practice.objects.User;
import practice.practice.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(User user) throws UserAlreadyExistsException {
        Long id = findUserIdByEmail(user.getEmail());
        if(id == -1) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setNonLocked(true);
            user.setEnabled(false);
            user.setRole(ApplicationUserRole.USER);
            return userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException(String.format("User with this %s email already exists", user.getEmail()));
        }
    }

    public boolean enableUserAccountById(Long id) {
        userRepository.enableUserAccount(id);
        User userById = findUserById(id);
        return  userById.isEnabled();
    }

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);

    }

    public List<User> getAllUsersOfPlatform() {
        return userRepository.findAll().stream()
                .filter(User::isEnabled)
                .collect(Collectors.toList());
    }

    public boolean authenticate(String email, String password) {
        var user = userRepository.findUserByEmail(email);
        return user.filter(value -> value.isEnabled() && value.getPassword().equals(password)).isPresent();
    }

    public Long findUserIdByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()) {
            return user.get().getId();
        } else {
            return -1L;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        return user.orElse(null);
    }
}
