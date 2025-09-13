package com.micro.piyush.movie.repository;

import com.micro.piyush.movie.entity.Theater;
import com.micro.piyush.movie.enums.LocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer>, JpaSpecificationExecutor<Theater> {

    Theater findByAddress(String address);

    // Find by theater name (case insensitive)
    Optional<Theater> findByNameIgnoreCase(String name);

    // Find by name with partial match
    @Query("SELECT t FROM Theater t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Theater> findByNameContaining(@Param("name") String name);

    // Find by location
    List<Theater> findByLocation(LocationType location);

    // Find by location with partial match
    @Query("SELECT t FROM Theater t WHERE LOWER(t.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Theater> findByLocationContaining(@Param("location") String location);

    // Check if theater name exists (for uniqueness validation)
    boolean existsByNameIgnoreCaseAndIdNot(String name, Integer id);

    // Find theaters with shows count
    @Query("SELECT t FROM Theater t LEFT JOIN FETCH t.shows")
    List<Theater> findAllWithShows();

    // Find theater by ID with shows and seats
    @Query("SELECT t FROM Theater t " +
            "LEFT JOIN FETCH t.shows s " +
            "LEFT JOIN FETCH t.showSeats ss " +
            "WHERE t.id = :id")
    Optional<Theater> findByIdWithShowsAndSeats(@Param("id") Integer id);

    // Search theaters with multiple criteria
    @Query("SELECT t FROM Theater t WHERE " +
            "(:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:location IS NULL OR t.location = :location) " +
            "AND (:address IS NULL OR LOWER(t.address) LIKE LOWER(CONCAT('%', :address, '%'))) " +
            "ORDER BY t.name")
    Page<Theater> searchTheaters(
            @Param("name") String name,
            @Param("location") LocationType location,
            @Param("address") String address,
            Pageable pageable);
}