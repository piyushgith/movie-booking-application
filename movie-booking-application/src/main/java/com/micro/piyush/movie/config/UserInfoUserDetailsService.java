package com.micro.piyush.movie.config;

import com.micro.piyush.movie.entity.User;
import com.micro.piyush.movie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = repository.findByEmailId(username);

        return userInfo.map(UserInfoUserDetails::new).orElseThrow(
                () -> new UsernameNotFoundException("user not found " + username));

    }
}
