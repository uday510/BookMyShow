package com.lld.bookmyshow.services;

import com.lld.bookmyshow.models.User;
import com.lld.bookmyshow.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(String email, String password) {
        // validate user
       Optional<User> userOptional =  userRepository.findByEmail(email);
       if (userOptional.isPresent()) {
           throw new RuntimeException("User Already exists");
       }

        User user = new User();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        user.setBookings(new ArrayList<>());

        user = userRepository.save(user);
        return user;
    }
    public User logIn(String email, String password){
        // 1. check if email is registered
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new RuntimeException("No user with the given Email!");
        }
        // 2. validate the password
        User user = userOptional.get();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(password,user.getPassword())){
            return user;
        }
        throw new RuntimeException("Invalid password!");
    }
}
