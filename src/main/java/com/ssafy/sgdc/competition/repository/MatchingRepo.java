package com.ssafy.sgdc.competition.repository;

import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.enums.IsSender;
import com.ssafy.sgdc.enums.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepo extends JpaRepository<Matching, Integer> {

    Optional<Matching> findByMatchingId(int matchingId);

    @Query("SELECT m FROM Matching m WHERE m.user.userId = :userId")
    List<Matching> findMatchingListByUserId(int userId);

    List<Matching> findByUserUserIdAndIsSender(int userId, IsSender isSender);

    Optional<Matching> findByCompetitionCompetIdAndUserUserId(int competId, int userId);

    // Feed Service에서 도전장 조회
    List<Matching> findByCompetitionCompetId(int competId);

    @Query("SELECT m FROM Matching m WHERE m.user.userId = :userId AND m.competition IS NOT NULL")
    List<Matching> findAcceptMatchingListByUserId(int userId);

    @Query("SELECT m FROM Matching m WHERE m.user.userId = :userId AND m.competition.competId = :competId")
    Optional<Matching> findAcceptMatchingByUserIdAndCompetId(int userId, int competId);

    @Query("SELECT m FROM Matching m WHERE m.competition.competId = :competId AND m.matchingId != :matchingId")
    Optional<Matching> findOtherMatching(int competId, int matchingId);

    Optional<Matching> findByCompetitionCompetIdAndIsSender(int competId, IsSender isSender);

    Optional<Matching> findMatchingByUserUserIdAndCategoryCategoryIdAndMatchStatusAndAndCompetExpirationTimeBetween(
            int userId, int categoryId, MatchStatus matchStatus, LocalDateTime startOfDay, LocalDateTime endOfDay
    );

}
