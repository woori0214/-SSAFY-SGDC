package com.ssafy.sgdc.solo.repository;

import com.ssafy.sgdc.enums.SoloResult;
import com.ssafy.sgdc.enums.SoloStatus;
import com.ssafy.sgdc.solo.domain.Solo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SoloRepo extends JpaRepository<Solo, Integer> {

    Optional<Solo> findSoloByUserUserIdAndCategoryCategoryIdAndSoloStatus(int userId, int categoryId, SoloStatus soloStatus);

    @Query("SELECT s FROM Solo s WHERE s.user.userId = :userId AND s.soloResult = :soloResult ORDER BY s.createAt DESC")
    List<Solo> findSoloList(int userId, SoloResult soloResult, Pageable pageable);

    List<Solo> findSoloByUserUserIdAndCreateAtBetween(int userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    Optional<Solo> findFirstByUserUserIdAndCategoryCategoryIdAndCreateAtBetween(int userId, int categoryId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Modifying
    @Query("UPDATE Solo s SET s.soloStatus = ?2 WHERE s.soloStatus = ?1")
    void  updateSoloStatus(SoloStatus currentStatus, SoloStatus updateStatus);

}
