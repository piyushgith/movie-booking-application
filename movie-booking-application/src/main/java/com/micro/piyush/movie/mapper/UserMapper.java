package com.micro.piyush.movie.mapper;

import com.micro.piyush.movie.entity.User;
import com.micro.piyush.movie.request.UserRequest;
import com.micro.piyush.movie.response.UserResponse;
import org.apache.commons.lang3.StringUtils;

public class UserMapper {
    public static User userDtoToUser(UserRequest userRequest, String password) {
        User user = User.builder()
                .name(userRequest.getName())
                .age(userRequest.getAge())
                .address(userRequest.getAddress())
                .gender(userRequest.getGenderType())
                .mobileNo(userRequest.getMobileNo())
                .emailId(userRequest.getEmailId())
                .password(password).build();

        if (null != userRequest.getId()) {
            user.setId(userRequest.getId());
        }
        return user;
    }

    public static UserResponse userToUserDto(User user) {
        UserResponse userDto = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .address(user.getAddress())
                .genderType(user.getGender())
                .mobileNo(user.getMobileNo())
                .emailId(user.getEmailId())
                .roles(user.getUserRoles())
                .build();
        return userDto;
    }

    /**
     *
     * @param existingUser
     * @param userRequest
     * @return
     */
    public static User userUpdate(User existingUser, UserRequest userRequest) {
        if (userRequest.getAge() != null)
            existingUser.setAge(userRequest.getAge());

        if (StringUtils.isBlank(userRequest.getAddress()))
            existingUser.setAddress(userRequest.getAddress());

        if (StringUtils.isBlank(userRequest.getEmailId()))
            existingUser.setEmailId(userRequest.getEmailId());

        if (StringUtils.isBlank(userRequest.getAddress()))
            existingUser.setMobileNo(userRequest.getAddress());

        if (StringUtils.isBlank(userRequest.getName()))
            existingUser.setName(userRequest.getName());

        return existingUser;
    }
}
