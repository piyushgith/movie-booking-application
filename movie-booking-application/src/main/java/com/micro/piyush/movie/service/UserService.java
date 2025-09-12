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

import java.util.*;
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
        // 1. Check if the user already exists.
        Optional<User> users = findUser(userRequest.getEmailId());
        if (users.isPresent()) {
            throw new UserExist();
        }

        // 2. Create the User entity from the request DTO.
        User newUser = UserMapper.userDtoToUser(userRequest, passwordEncoder.encode(userRequest.getPassword()));

        // 3. Create the single UserRole entity based on the business logic.
        UserRole userRole = new UserRole();
        userRole.setUserRole(userRequest.getRole());
        userRole.setUser(newUser);
        userRole.setId(new UserRoleId(null,userRequest.getRole()));

        Set<UserRole> userRolesSet = new HashSet<>();
        userRolesSet.add(userRole);

        // 4. Establish the bidirectional relationship using the helper method.
        // This is the most crucial step. It links the User and UserRole objects
        // in memory before they are saved to the database.
        newUser.setUserRoles(userRolesSet);

        // 5. Save the User entity.
        // Due to the `cascade = CascadeType.ALL` on the `userRoles` relationship,
        // JPA will automatically save the `newUser` and its associated `userRole`
        // in a single transaction. The user's ID will be generated and then
        // automatically populated in the `user_id` column of the `user_role` table.
        return userRepository.save(newUser);
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

    public void deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        }
    }
}
