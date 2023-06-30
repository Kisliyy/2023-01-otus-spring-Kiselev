package ru.otus.book_storage.service.users;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.book_storage.dao.user.UserRepository;
import ru.otus.book_storage.models.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @CircuitBreaker(name = "UserService_circuitbreaker")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.hasText(username)) {
            Optional<User> byUsername = userRepository.findByUsername(username);
            return byUsername
                    .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + username));
        }
        throw new BadCredentialsException("Username must not be empty!");
    }


}
