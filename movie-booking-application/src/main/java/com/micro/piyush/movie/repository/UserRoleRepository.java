package com.micro.piyush.movie.repository;

import com.micro.piyush.movie.entity.UserRole;
import com.micro.piyush.movie.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for the UserRole entity.
 * It uses the UserRoleId as the composite key type.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    // This interface provides standard CRUD operations for the UserRole entity.
}
