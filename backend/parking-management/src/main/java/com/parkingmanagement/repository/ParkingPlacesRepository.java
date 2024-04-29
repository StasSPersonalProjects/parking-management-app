package com.parkingmanagement.repository;

import com.parkingmanagement.dto.FreePlacesCount;
import com.parkingmanagement.entities.ParkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public interface ParkingPlacesRepository extends JpaRepository<ParkingPlace, Integer> {

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE parking_places
            SET is_Free = NOT is_Free
            WHERE number = :number""", nativeQuery = true)
    void setNewIsFreeStatus(@Param("number") int number);

    @Query(value = """
            SELECT floor, COUNT(*) AS free_places_count
            FROM parking_places
            WHERE is_Free = true
            GROUP BY floor""", nativeQuery = true)
    List<Object[]> findAllFreePlacesOnEachFloorNative();

    default List<FreePlacesCount> findAllFreePlacesOnEachFloor() {
        List<Object[]> resultList = findAllFreePlacesOnEachFloorNative();
        return resultList.stream()
                .map(result -> new FreePlacesCount((Integer) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

}
