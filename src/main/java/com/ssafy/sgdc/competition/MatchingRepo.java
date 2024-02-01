package com.ssafy.sgdc.competition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepo extends JpaRepository<Matching, Integer> {



}
