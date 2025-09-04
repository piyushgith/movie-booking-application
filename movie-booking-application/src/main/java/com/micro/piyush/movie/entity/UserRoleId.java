package com.micro.piyush.movie.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class UserRoleId implements Serializable {

    // These fields are part of the composite key and are mapped in the UserRole entity
    private Integer userId;
    private String role;

    // Default constructor is required by JPA
    public UserRoleId() {
    }

    // It is crucial to override equals() and hashCode() for composite keys
    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return userId.equals(that.userId) && role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return 31 * userId.hashCode() + role.hashCode();
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}