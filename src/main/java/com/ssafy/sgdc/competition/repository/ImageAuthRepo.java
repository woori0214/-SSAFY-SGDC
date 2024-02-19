package com.ssafy.sgdc.competition.repository;

import com.ssafy.sgdc.competition.domain.ImageAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageAuthRepo extends JpaRepository<ImageAuth, Integer> {

    int countByCompetitionCompetId(int competId);
    Optional<ImageAuth> findByCompetitionCompetId(int competId);

    Optional<ImageAuth> findByMatcingMatchingId(int matchingId);

}
