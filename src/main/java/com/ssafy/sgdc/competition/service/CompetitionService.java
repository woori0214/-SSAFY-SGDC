package com.ssafy.sgdc.competition.service;

import com.ssafy.sgdc.category.CategoryRepo;
import com.ssafy.sgdc.competition.dto.CompetitionDto;
import com.ssafy.sgdc.competition.dto.request.CompetitionProgressDetailDto;
import com.ssafy.sgdc.competition.dto.request.CreateCompetDetailDto;
import com.ssafy.sgdc.competition.dto.request.CreateImageAuthDto;
import com.ssafy.sgdc.competition.repository.CompetDetailRepo;
import com.ssafy.sgdc.competition.repository.CompetitionRepo;
import com.ssafy.sgdc.competition.repository.ImageAuthRepo;
import com.ssafy.sgdc.competition.repository.MatchingRepo;
import com.ssafy.sgdc.competition.domain.CompetDetail;
import com.ssafy.sgdc.competition.domain.Competition;
import com.ssafy.sgdc.competition.domain.ImageAuth;
import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.competition.dto.MatchingDto;
import com.ssafy.sgdc.competition.dto.request.CreateMatchingDto;
import com.ssafy.sgdc.enums.CompetKind;
import com.ssafy.sgdc.enums.CompetResult;
import com.ssafy.sgdc.enums.IsSender;
import com.ssafy.sgdc.enums.MatchStatus;
import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final MatchingRepo matchingRepo;

    private final UserRepo userRepo;

    private final CategoryRepo categoryRepo;

    private final CompetitionRepo competitionRepo;

    private final ImageAuthRepo imageAuthRepo;

    private final CompetDetailRepo competDetailRepo;

    /* !!도전장 보내기 관련
        프론트에서 사용자가 진행 중인 경쟁목록을 가져와서 애초에 진행 중인 카테고리로는 보낼 수 없게 막아야함
        백 에서 하려면 join을 많이 해야해서..
     */

    @Transactional
    // 랜덤 상대에게 도전장 보내기
    public Matching sendRandomMatching(int userId, int categoryId) {
        // 사용자. 도전장 보내는 주체.
        User user = userRepo.findByUserId(userId);

        //도전장 개수 빼기
        if (user.getChallengeCnt() <= 0) {
            throw new RuntimeException("남은 도전장 없음");
        }
        user.setChallengeCnt(user.getChallengeCnt() - 1);

        User randomUser = getRandomUser(userId);

//        // 보내는 사람 도전장
        CreateMatchingDto sendMatchingDto = new CreateMatchingDto(user,
                categoryRepo.findByCategoryId(categoryId), CompetKind.RANDOM, IsSender.Y,
                LocalDateTime.now().plusHours(2), MatchStatus.WAIT);

//        //받는 사람 도전장
        CreateMatchingDto receiveMatchingDto = new CreateMatchingDto(randomUser,
                categoryRepo.findByCategoryId(categoryId), CompetKind.RANDOM, IsSender.N,
                LocalDateTime.now().plusHours(2), MatchStatus.WAIT);

        matchingRepo.save(CreateMatchingDto.from(receiveMatchingDto));

        return matchingRepo.save(CreateMatchingDto.from(sendMatchingDto));

    }

    // 랜덤 상대를 뽑는 함수
    // 중간에 아이디가 빈 값을 고려하여 최소값 최대값의 범위로 하나 뽑음
    // TODO: 친구인 상대 제외하기
    private User getRandomUser(int userId) {

        int minId = userRepo.findMinUserId();
        int maxId = userRepo.findMaxUserId();
        Random random = new Random();

        User randomUser;
        do {
            int randomId = minId + (int) (random.nextDouble() * (maxId - minId));
            randomUser = userRepo.findByUserId(randomId);
        } while (randomUser == null || randomUser.getUserId() == userId);
        //유저가 없거나 보낸 사람 아이디가 값으면 다시 뽑음

        return randomUser;
    }

    @Transactional
    // 친구에게 도전장 보내기
    // TODO: 같은 카테고리로 보내지 못하게 하기
    public Matching sendFriendMatching(int userId, int friendId, int categoryId) {

        if (userId == friendId) {
            throw new RuntimeException("자신에게 도전장을 보낼 수 없습니다.");
        }

        User user = userRepo.findByUserId(userId);

        //도전장 개수 빼기
        if (user.getChallengeCnt() <= 0) {
            throw new RuntimeException("남은 도전장 없음");
        }
        user.setChallengeCnt(user.getChallengeCnt() - 1);

        User Friend = userRepo.findByUserId(friendId);

        // 보내는 사람 도전장
        CreateMatchingDto sendMatchingDto = new CreateMatchingDto(user,
                categoryRepo.findByCategoryId(categoryId), CompetKind.FRIEND, IsSender.Y,
                LocalDateTime.now().plusHours(2), MatchStatus.WAIT);

        //받는 사람 도전장
        CreateMatchingDto receiveMatchingDto = new CreateMatchingDto(Friend,
                categoryRepo.findByCategoryId(categoryId), CompetKind.FRIEND, IsSender.N,
                LocalDateTime.now().plusHours(2), MatchStatus.WAIT);


        matchingRepo.save(CreateMatchingDto.from(receiveMatchingDto));

        return matchingRepo.save(CreateMatchingDto.from(sendMatchingDto));

    }

    // 도전장 수락
    // TODO: 수락은 한번만 누를 수 있게
    public Matching acceptMatching(int matchingId) {

        Matching recieveMatching = matchingRepo.findByMatchingId(matchingId)
                .orElseThrow(() -> new RuntimeException("도전장 정보를 찾을 수 없습니다."));

        Matching sendMatching = matchingRepo.findByMatchingId(matchingId + 1)
                .orElseThrow(() -> new RuntimeException("도전장 정보를 찾을 수 없습니다."));

        // 도전장 만료 시간 확인
        if (recieveMatching.getCompetExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("도전장이 만료됐습니다.");
        }


        // 경기 생성
        Competition competition = Competition.of(LocalDateTime.now(),
                recieveMatching.getCompetExpirationTime().plusHours(4));
        competitionRepo.save(competition);

        //TODO: 권한 있는 사람만 바꿀수 있게 조절.
        //도전장 상태 업데이트
        recieveMatching.setMatchStatus(MatchStatus.ACCEPT);
        sendMatching.setMatchStatus(MatchStatus.ACCEPT);
        recieveMatching.setCompetition(competition);
        sendMatching.setCompetition(competition);

        matchingRepo.save(sendMatching);

        return matchingRepo.save(recieveMatching);

    }

    // 도전장 리스트 반환
    public List<MatchingDto> getmatchingList(int userId) {
        return matchingRepo.findMatchingListByUserId(userId).stream().map(MatchingDto::of).toList();
    }



    //종료된 경쟁 목록 조회
    public List<CompetitionDto> getCompleteCompetitionList(int userId) {

        List<CompetitionDto> competitionDtoList = new ArrayList<>();

        List<Matching> matchings = matchingRepo.findAcceptMatchingListByUserId(userId);

        for (Matching matching : matchings) {

            // 경쟁 결과가 나온 경쟁인지 확인
            if (matching.getCompetition().getCompetDetail() != null) {

                Matching otherMatching = matchingRepo.findOtherMatching(matching.getCompetition().getCompetId(),
                                matching.getMatchingId())
                        .orElseThrow(() -> new RuntimeException("상대방 도전장 없음"));
                CompetitionDto competitionDto = CompetitionDto.of(
                        matching.getCompetition(),
                        matching.getMatchingId(),
                        matching.getIsSender(),
                        otherMatching.getUser().getUserId(),
                        otherMatching.getUser().getUserNickname()
                );

                competitionDtoList.add(competitionDto);
            }
        }

        return competitionDtoList;
    }

    // 종료된 경쟁 조회
    public CompetitionDto getCompleteCompetition(int userId, int competId) {
        Matching matching = matchingRepo.findAcceptMatchingByUserIdAndCompetId(userId, competId)
                .orElseThrow(() -> new RuntimeException("해당 경쟁을 찾을 수 없습니다."));

        // 종료되지 않은 경쟁
        if (matching.getCompetition().getCompetDetail() == null) {
            throw new RuntimeException("아직 종료되지 않은 경쟁입니다.");
        }

        Matching oherMatching = matchingRepo.findOtherMatching(matching.getCompetition().getCompetId(),
                        matching.getMatchingId())
                .orElseThrow(() -> new RuntimeException("상대방 도전장 없음"));

        return CompetitionDto.of(
                matching.getCompetition(),
                matching.getMatchingId(),
                matching.getIsSender(),
                oherMatching.getUser().getUserId(),
                oherMatching.getUser().getUserNickname()
        );
    }

    /*
    API로 처리하면 프론트에서 일정 시간마다 불러와야 됨
     */
    // 경쟁 모드 인증 현황
    public List<CompetitionProgressDetailDto> getProgressCompetitionDetail(int userId) {

        List<CompetitionProgressDetailDto> details = new ArrayList<>();

        List<Matching> matchings = matchingRepo.findAcceptMatchingListByUserId(userId);

        for (Matching matching : matchings) {

            // 경쟁 결과가 아직 나오지 않은 경쟁만
            if (matching.getCompetition().getCompetDetail() == null) {
                Matching otherMatching = matchingRepo.findOtherMatching(matching.getCompetition().getCompetId(),
                                matching.getMatchingId())
                        .orElseThrow(() -> new RuntimeException("상대방 도전장 없음"));

                ImageAuth userImageAuth = imageAuthRepo.findByMatcingMatchingId(matching.getMatchingId())
                        .orElse(null);

                ImageAuth otherImageAuth = imageAuthRepo.findByMatcingMatchingId(otherMatching.getMatchingId())
                        .orElse(null);

                CompetitionProgressDetailDto competitionProgressDetailDto
                        = CompetitionProgressDetailDto.of(
                                matching.getCompetition().getCompetId(),
                        matching.getCategory().getCategoryId(),
                        userImageAuth != null ? userImageAuth.getAuthId() : 0, // null이면 0으로 처리함
                        userImageAuth != null ? userImageAuth.getAuthImg() : null,
                        matching.getUser().getUserId(),
                        matching.getUser().getUserImg(),
                        matching.getUser().getUserNickname(),
                        otherImageAuth != null ? otherImageAuth.getAuthId() : 0, // null이면 0으로 처리함
                        otherImageAuth != null ? otherImageAuth.getAuthImg() : null,
                        otherMatching.getUser().getUserId(),
                        otherMatching.getUser().getUserImg(),
                        otherMatching.getUser().getUserNickname()
                );

                details.add(competitionProgressDetailDto);

            }

        }

        return details;

    }

    // 일정 시간마다 경쟁결과 확인
    //TODO: 리펙토링
    @Scheduled(fixedDelay = 6000000) // 1 분 주기
    public void updateCompetition() {

        // 시간이 다 됐는데 결과가 나오지 않은 경쟁 리스트 조회(사진 안 보낸 경우)
        List<Competition> competitions = competitionRepo.findDoneCompetitions();

        for (Competition competition : competitions) {

            int imageAuthCount = imageAuthRepo.countByCompetitionCompetId(
                    competition.getCompetId()
            );

            CompetDetail competDetail = new CompetDetail();
//            CompetDetail competDetail;
//            competDetail = CompetDetail.of(CompetResult.BOTH_FAIL);

            if (imageAuthCount == 0) { // 둘 다 인증하지 않은 경우

                competDetail.setCompetResult(CompetResult.BOTH_FAIL);
            } else if (imageAuthCount == 1) { // 둘 중 한 명만 인증한 경우

                // 방법 1.
                // 도전장을 경쟁아이디로 찾음
                // 찾은 도전장 아이디를 사진인증에서 찾음
                // 도전장 발신자여부가 Y이면 CompetResult를 send_WIN

                // 방법 2. (단순화 방법)
                // 보낸 사람의 도전장 id는 항상 짝수, 받은 사람은 홀수
                // image_auth의 matching_id를 확인하여 상태값 설정
                ImageAuth imageAuth = imageAuthRepo.findByCompetitionCompetId(
                        competition.getCompetId()).orElseThrow(() -> new RuntimeException("경쟁에 해당하는 이미지 인증이 없습니다."));

                // TODO: 멀티쓰레드 환경에서 블로킹 처리나 , Concurrent 처리를 통해서 무결성 유지 가능.
                if (imageAuth.getMatcing().getMatchingId() % 2 == 0) {
                    competDetail.setCompetResult(CompetResult.SEND_WIN);
                } else {
                    competDetail.setCompetResult(CompetResult.RECEIVE_WIN);
                }

            } else { // 둘 다 인증 한 경우 (이건 사진 인증에서 확인하므로 아마 여기까진 안올것)
                competDetail.setCompetResult(CompetResult.BOTH_WIN);
            }

            competition.setCompetDetail(competDetailRepo.save(competDetail));

            competitionRepo.save(competition);

        }

    }

}
