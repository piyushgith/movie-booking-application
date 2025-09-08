package com.micro.piyush.movie.repository;

import com.micro.piyush.movie.entity.Theater;
import com.micro.piyush.movie.enums.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    Theater findByAddress(String address);

    List<Theater> findByLocation(LocationType location);
}