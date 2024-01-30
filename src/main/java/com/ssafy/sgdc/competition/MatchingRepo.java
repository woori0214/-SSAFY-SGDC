package com.ssafy.sgdc.competition;

import com.ssafy.sgdc.domain.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepo extends JpaRepository<Matching, Long> {



}
