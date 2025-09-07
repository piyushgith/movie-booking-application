package com.micro.piyush.movie.controller;


import com.micro.piyush.movie.entity.User;
import com.micro.piyush.movie.response.JWTTokenResponse;
import com.micro.piyush.movie.response.UserResponse;
import com.micro.piyush.movie.service.JWTService;
import com.micro.piyush.movie.request.AuthRequest;
import com.micro.piyush.movie.request.UserRequest;
import com.micro.piyush.movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/addNew")
    public ResponseEntity<Object> addNewUser(@RequestBody UserRequest userEntryDto) {
        try {
            User result = userService.addUser(userEntryDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/getToken")
    public JWTTokenResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmailId(), authRequest.getPassword()));
        String[] role = null;
        if (authentication.isAuthenticated()) {
            Optional<User> user = userService.findUser(authRequest.getEmailId());
            if (user.isPresent())
                role = user.get().getUserRoles().stream()
                        .map(userRole -> userRole.getUserRole())
                        .toArray(String[]::new);
            if (role.length > 0) {
                return new JWTTokenResponse(jwtService.generateToken(authRequest.getEmailId()), role[0]);
            }
        }
        throw new UsernameNotFoundException("invalid user details.");
    }

    @GetMapping("/allUsers")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/updateUser")
    public UserResponse updateUser(@RequestBody UserRequest userEntryDto) {
        return userService.updateUser(userEntryDto);
    }

}