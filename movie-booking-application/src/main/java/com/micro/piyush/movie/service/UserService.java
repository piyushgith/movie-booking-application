package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.User;
import com.micro.piyush.movie.entity.UserRole;
import com.micro.piyush.movie.entity.UserRoleId;
import com.micro.piyush.movie.exception.UserDoesNotExists;
import com.micro.piyush.movie.exception.UserExist;
import com.micro.piyush.movie.mapper.UserMapper;
import com.micro.piyush.movie.repository.UserRepository;
import com.micro.piyush.movie.repository.UserRoleRepository;
import com.micro.piyush.movie.request.UserRequest;
import com.micro.piyush.movie.response.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Optional<User> findUser(String emailId) {
        Optional<User> users = userRepository.findByEmailId(emailId);
        return users;
    }

    /**
     * Saves a new user and assigns a set of roles to them.
     * The method uses a transactional approach to ensure data integrity.
     *
     * @param userRequest The User entity to be saved.
     * A Set of role names (e.g., "ADMIN", "USER") to assign to the user.
     * @return The saved User entity.
     */
    @Transactional
    public User addUser(UserRequest userRequest) {
        Optional<User> users = findUser(userRequest.getEmailId());
        if (users.isPresent()) {
            throw new UserExist();
        }
        // 1. Save the User entity first.
        // This is crucial because the user's ID needs to be generated before
        // it can be used in the UserRole composite key.
        User savedUser = UserMapper.userDtoToUser(userRequest, passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(savedUser);

        // 2. Clear existing roles and prepare the new set for the user.
        // This handles both new users and updating existing users' roles.
        savedUser.setUserRoles(new HashSet<>());

        // 3. Create and save each UserRole entity for the new user.
        // Now we are assigning only 1 role per user so no need of loop
        // Create the composite key for the UserRole entity.
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(savedUser.getId());
        userRoleId.setRole(userRequest.getRoles());

        // Create the UserRole entity itself.
        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setUser(savedUser);

        // Add the new role to the user's set of roles.
        savedUser.getUserRoles().add(userRole);

        // No need to explicitly save the UserRole entity.
        // @Transactional will save it

        return savedUser;
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> responseList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        if (userList.size() > 0) {
            responseList = userList.stream()
                    .map(UserMapper::userToUserDto)
                    .collect(Collectors.toList());
        }
        return responseList;
    }

    public UserResponse updateUser(UserRequest userUpdateRequest) {
        Optional<User> user = findUser(userUpdateRequest.getEmailId());
        if (!user.isPresent()) {
            throw new UserDoesNotExists();
        }
        User updatedUser = UserMapper.userUpdate(user.get(), userUpdateRequest);
        updatedUser = userRepository.save(updatedUser);
        return UserMapper.userToUserDto(updatedUser);
    }
}
