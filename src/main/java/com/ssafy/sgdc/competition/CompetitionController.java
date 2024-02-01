package com.ssafy.sgdc.competition;

import com.ssafy.sgdc.competition.dto.RequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/compet")
public class CompetitionController {

    @Autowired
    private MatchingService matchingService;

    @PostMapping("/random-send")
    public ResponseEntity<?> randomSend(@RequestBody RequestData requestData) {
        Matching result = matchingService.sendRandomMatching(requestData.getUserId(), requestData.getCategoryId());
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "도전장 보내기 성공");
        response.put("user_nickname", result.getUser().getUserNickname());
        return ResponseEntity.ok(response);
    }

}
