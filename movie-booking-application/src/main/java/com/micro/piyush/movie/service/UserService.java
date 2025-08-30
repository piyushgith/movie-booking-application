package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.User;
import com.micro.piyush.movie.exception.UserExist;
import com.micro.piyush.movie.mapper.UserMapper;
import com.micro.piyush.movie.repository.UserRepository;
import com.micro.piyush.movie.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String addUser(UserRequest userRequest) {
        Optional<User> users = userRepository.findByEmailId(userRequest.getEmailId());
        if (users.isPresent()) {
            throw new UserExist();
        }
        User user = UserMapper.userDtoToUser(userRequest, passwordEncoder.encode("1234"));
        userRepository.save(user);
        return "User Saved Successfully";
    }
}
