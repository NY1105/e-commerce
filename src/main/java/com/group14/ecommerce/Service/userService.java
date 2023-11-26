package com.group14.ecommerce.Service;

import com.group14.ecommerce.Repository.userRepository;
import com.group14.ecommerce.Vo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userService {
    private final userRepository user_repository;
    public userService(userRepository userRepository) {
        this.user_repository = userRepository;
    }

    public User register(User newUser){
        User user = user_repository.saveAndFlush(newUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED).getBody();
    }

    public Optional<User> auth(User user){
        Optional<User> authed_user = user_repository.findById(user.getUserId());
        if (authed_user.isPresent()) {                          
            String password = authed_user.get().getUserPassword();
            String input = user.getUserPassword();
            if (password.equals(input)) {
                return authed_user;
            }
        }
        return Optional.empty();
    }


}
