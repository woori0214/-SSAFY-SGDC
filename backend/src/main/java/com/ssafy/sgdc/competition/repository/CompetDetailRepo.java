package com.ssafy.sgdc.competition.repository;

import com.ssafy.sgdc.competition.domain.CompetDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetDetailRepo extends JpaRepository<CompetDetail, Integer> {

}
