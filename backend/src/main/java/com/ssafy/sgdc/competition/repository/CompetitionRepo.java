package com.ssafy.sgdc.competition.repository;

import com.ssafy.sgdc.competition.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepo extends JpaRepository<Competition, Integer> {

    Optional<Competition> findByCompetId(int competId);

    @Query("SELECT c FROM Competition c WHERE c.competDetail IS NULL AND c.doneAt < CURRENT_TIMESTAMP")
    List<Competition> findDoneCompetitions();

}
