package com.ssafy.sgdc.competition.service;

import com.ssafy.sgdc.category.Category;
import com.ssafy.sgdc.category.UserCategory;
import com.ssafy.sgdc.category.dto.UserCategoryDto;
import com.ssafy.sgdc.category.repository.CategoryRepo;
import com.ssafy.sgdc.category.repository.UserCategoryRepo;
import com.ssafy.sgdc.competition.dto.CompetitionDto;
import com.ssafy.sgdc.competition.dto.request.CompetitionProgressDetailDto;
import com.ssafy.sgdc.competition.dto.request.CreateCompetDetailDto;
import com.ssafy.sgdc.competition.repository.CompetDetailRepo;
import com.ssafy.sgdc.competition.repository.CompetitionRepo;
import com.ssafy.sgdc.competition.repository.ImageAuthRepo;
import com.ssafy.sgdc.competition.repository.MatchingRepo;
import com.ssafy.sgdc.competition.domain.Competition;
import com.ssafy.sgdc.competition.domain.ImageAuth;
import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.competition.dto.MatchingDto;
import com.ssafy.sgdc.competition.dto.request.CreateMatchingDto;
import com.ssafy.sgdc.enums.CompetKind;
import com.ssafy.sgdc.enums.CompetResult;
import com.ssafy.sgdc.enums.IsSender;
import com.ssafy.sgdc.enums.MatchStatus;
import com.ssafy.sgdc.feed.service.FeedService;
import com.ssafy.sgdc.enums.*;
import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private final FeedService feedService;

    private final UserCategoryRepo userCategoryRepo;

    @Transactional
    // 랜덤 상대에게 도전장 보내기
    // TODO: 같은 카테고리 도전장을 같은 사람에게 여러번 못보내게 해야됨
    // TODO: 현재 같은 일을 하는 로직이 많아서 함수로 빼는게 좋아보임
    public Matching sendRandomMatching(int userId, int categoryId) {

        // 해당 카테고리 경기를 진행 중이면 막아주는 로직
        Optional<UserCategory> optionalUserCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(userId, categoryId);

        if (optionalUserCategory.isPresent()) {
            UserCategory userCategory = optionalUserCategory.get();
            if (userCategory.getCategoryStatus() == CategoryStatus.PLAY_STATUS) {
                throw new RuntimeException("해당 카테고리는 현재 진행 중입니다.");
            }
        }

        // 사용자. 도전장 보내는 주체.
        User user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("sendRandomMatching -> 해당 유저를 찾을 수 없습니다."));

        //도전장 개수 빼기
        if (user.getChallengeCnt() <= 0) {
            throw new RuntimeException("남은 도전장 없음");
        }
        user.setChallengeCnt(user.getChallengeCnt() - 1);

        User randomUser = getRandomUser(userId, categoryId);

        Category category = categoryRepo.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("sendRandomMatching -> 해당 카테고리가 존재하지 않습니다."));

        // 보내는 사람 도전장
        CreateMatchingDto sendMatchingDto = new CreateMatchingDto(user,
                category, CompetKind.RANDOM, IsSender.Y,
                LocalDateTime.now().plusHours(2), MatchStatus.WAIT);

        //받는 사람 도전장
        CreateMatchingDto receiveMatchingDto = new CreateMatchingDto(randomUser,
                category, CompetKind.RANDOM, IsSender.N,
                LocalDateTime.now().plusHours(2), MatchStatus.WAIT);

        // 유저 카테고리 진행 상태 업데이트
        categoryStatusUpdate(userId, randomUser.getUserId(), categoryId, CategoryStatus.MATCH_STATUS);

        matchingRepo.save(CreateMatchingDto.from(receiveMatchingDto));

        return matchingRepo.save(CreateMatchingDto.from(sendMatchingDto));

    }

    // 랜덤 상대를 뽑는 함수
    // 중간에 아이디가 빈 값을 고려하여 최소값 최대값의 범위로 하나 뽑음
    // 상대방이 해당 카테고리를 진행 중이면 다시 뽑음
    // TODO: 친구인 상대 제외하기 -> 랜덤으로 친구가 걸려도 괜찮을 듯
    private User getRandomUser(int userId, int categoryId) {

        int minId = userRepo.findMinUserId();
        int maxId = userRepo.findMaxUserId();
        Random random = new Random();

        User randomUser;
        boolean isPlayingCategory;
        do {
            int randomId = minId + (int) (random.nextDouble() * (maxId - minId));
            randomUser = userRepo.findByUserId(randomId)
                    .orElse(null);

            // 랜덤 상대가 해당 카테고리 경기를 진행 중이면 막아주는 로직
            isPlayingCategory = randomUser != null &&
                    userCategoryRepo.findByUserUserIdAndCategoryCategoryIdAndCategoryStatus(
                            randomId, categoryId, CategoryStatus.PLAY_STATUS
                    ).isPresent();

        } while (randomUser == null || randomUser.getUserId() == userId || isPlayingCategory);
        //유저가 없거나 보낸 사람 아이디가 값으면 다시 뽑음

        return randomUser;
    }

    @Transactional
    // 친구에게 도전장 보내기
    // TODO: 같은 카테고리 도전장을 같은 사람에게 여러번 못보내게 해야됨
    public Matching sendFriendMatching(int userId, int friendId, int categoryId) {

        // 해당 카테고리 경기를 진행 중이면 막아주는 로직
        Optional<UserCategory> optionalUserCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(userId, categoryId);

        if (optionalUserCategory.isPresent()) {
            UserCategory userCategory = optionalUserCategory.get();
            if (userCategory.getCategoryStatus() == CategoryStatus.PLAY_STATUS) {
                throw new RuntimeException("해당 카테고리는 현재 진행 중입니다.");
            }
        }

        // 해당 카테고리 경기를 친구가 진행 중이면 막아주는 로직
        Optional<UserCategory> optionalFriendCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(friendId, categoryId);

        if (optionalFriendCategory.isPresent()) {
            UserCategory friendCategory = optionalFriendCategory.get();
            if (friendCategory.getCategoryStatus() == CategoryStatus.PLAY_STATUS) {
                throw new RuntimeException("상대방이 해당 카테고리를 현재 진행 중입니다.");
            }
        }

        if (userId == friendId) {
            throw new RuntimeException("자신에게 도전장을 보낼 수 없습니다.");
        }

        User user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("sendFriendMatching -> 해당 유저(자신)를 찾을 수 없습니다."));

        //도전장 개수 빼기
        if (user.getChallengeCnt() <= 0) {
            throw new RuntimeException("남은 도전장 없음");
        }
        user.setChallengeCnt(user.getChallengeCnt() - 1);

        User Friend = userRepo.findByUserId(friendId)
                .orElseThrow(() -> new RuntimeException("sendFriendMatching -> 해당 친구를 찾을 수 없습니다."));

        Category category = categoryRepo.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("sendFriendMatching -> 해당 카테고리가 존재하지 않습니다."));


        // 보내는 사람 도전장
        CreateMatchingDto sendMatchingDto = new CreateMatchingDto(user,
                category, CompetKind.FRIEND, IsSender.Y,
                LocalDateTime.now().plusHours(2), MatchStatus.WAIT);

        //받는 사람 도전장
        CreateMatchingDto receiveMatchingDto = new CreateMatchingDto(Friend,
                category, CompetKind.FRIEND, IsSender.N,
                LocalDateTime.now().plusHours(2), MatchStatus.WAIT);

        // 유저 카테고리 진행 상태 업데이트
        categoryStatusUpdate(userId, friendId, categoryId, CategoryStatus.MATCH_STATUS);

        matchingRepo.save(CreateMatchingDto.from(receiveMatchingDto));

        return matchingRepo.save(CreateMatchingDto.from(sendMatchingDto));

    }

    @Transactional
    public void categoryStatusUpdate(int sendUserId, int receiveUserId, int categoryId, CategoryStatus categoryStatus) {
        UserCategory sendUserCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(
                        sendUserId, categoryId
                ).orElseThrow(() -> new RuntimeException("보낸 사람의 유저 카테고리가 존재하지 않습니다."));

        UserCategory receiveUserCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(
                        receiveUserId, categoryId
                ).orElseThrow(() -> new RuntimeException("받은 사람의 유저 카테고리가 존재하지 않습니다."));

        sendUserCategory.updateCategoryStatus(categoryStatus);

        receiveUserCategory.updateCategoryStatus(categoryStatus);
    }

    // 도전장 수락
    @Transactional
    public Matching acceptMatching(int matchingId) {

        Matching recieveMatching = matchingRepo.findByMatchingId(matchingId)
                .orElseThrow(() -> new RuntimeException("도전장 정보를 찾을 수 없습니다."));

        // TODO: 로직 바꿔야하는데 어떻게??? 경쟁이 생성되기 전이라 찾기 힘듦
        Matching sendMatching = matchingRepo.findByMatchingId(matchingId + 1)
                .orElseThrow(() -> new RuntimeException("도전장 정보를 찾을 수 없습니다."));

        // 도전장 만료 시간 확인
        if (recieveMatching.getCompetExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("도전장이 만료됐습니다.");
        }

        // 도전장 수락 상태 확인
        if (recieveMatching.getMatchStatus() == MatchStatus.ACCEPT) {
            throw new RuntimeException("이미 수락한 도전장입니다.");
        }

        // 경기 생성
        Competition competition = Competition.of(LocalDateTime.now(),
                recieveMatching.getCompetExpirationTime().plusMinutes(4));
        competitionRepo.save(competition);

        //도전장 상태 업데이트
        recieveMatching.updateMatchingStatus(MatchStatus.ACCEPT);
        sendMatching.updateMatchingStatus(MatchStatus.ACCEPT);
        recieveMatching.updateCompetition(competition);
        sendMatching.updateCompetition(competition);

        // 유저 카테고리 상태 업데이트
        categoryStatusUpdate(sendMatching.getUser().getUserId(),
                recieveMatching.getUser().getUserId(),
                sendMatching.getCategory().getCategoryId(),
                CategoryStatus.PLAY_STATUS
        );

        return recieveMatching;

    }

    // 도전장 리스트 반환
    public List<MatchingDto> getMatchingList(int userId) {
        return matchingRepo.findMatchingListByUserId(userId).stream().map(MatchingDto::of).toList();
    }

    // 받은 도전장 리스트 반환
    public List<MatchingDto> getReceiveMatchingList(int userId) {
        return matchingRepo.findByUserUserIdAndIsSender(userId, IsSender.N).stream().map(MatchingDto::of).toList();
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

                ImageAuth userImageAuth =
                        imageAuthRepo.findByMatcingMatchingId(matching.getMatchingId()).orElse(null);
                ImageAuth otherImageAuth =
                        imageAuthRepo.findByMatcingMatchingId(otherMatching.getMatchingId()).orElse(null);

                CompetitionDto competitionDto = CompetitionDto.of(
                        matching.getCompetition(),
                        matching.getMatchingId(),
                        matching.getIsSender(),
                        otherMatching.getUser().getUserId(),
                        otherMatching.getUser().getUserNickname(),
                        userImageAuth != null ? userImageAuth.getAuthImg() : null,
                        otherImageAuth != null ? otherImageAuth.getAuthImg() : null,
                        matching.getCompetition().getDoneAt()
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

        Matching otherMatching = matchingRepo.findOtherMatching(matching.getCompetition().getCompetId(),
                        matching.getMatchingId())
                .orElseThrow(() -> new RuntimeException("상대방 도전장 없음"));

        ImageAuth userImageAuth =
                imageAuthRepo.findByMatcingMatchingId(matching.getMatchingId()).orElse(null);
        ImageAuth otherImageAuth =
                imageAuthRepo.findByMatcingMatchingId(otherMatching.getMatchingId()).orElse(null);

        return CompetitionDto.of(
                matching.getCompetition(),
                matching.getMatchingId(),
                matching.getIsSender(),
                otherMatching.getUser().getUserId(),
                otherMatching.getUser().getUserNickname(),
                userImageAuth != null ? userImageAuth.getAuthImg() : null,
                otherImageAuth != null ? otherImageAuth.getAuthImg() : null,
                matching.getCompetition().getDoneAt()
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

    // 유저 카테고리 목록 조회
    public List<UserCategoryDto> getUserCategoryList(int userId) {
        return userCategoryRepo.findUserCategoryByUserUserId(userId).stream().map(UserCategoryDto::of).toList();
    }

    public UserCategoryDto getUserCategory(int userId, int categoryId) {
        UserCategory userCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(userId, categoryId)
                        .orElseThrow(() -> new RuntimeException("해당 유저의 유저 카테고리가 존재하지 않습니다."));

        return UserCategoryDto.of(userCategory);
    }

    // 일정 시간마다 경쟁결과 확인
    @Transactional
    @Scheduled(fixedDelay = 6000000) // 100 분 주기
    public void updateCompetition() {

        // 시간이 다 됐는데 결과가 나오지 않은 경쟁 리스트 조회(사진 안 보낸 경우)
        List<Competition> competitions = competitionRepo.findDoneCompetitions();

        for (Competition competition : competitions) {

            int imageAuthCount = imageAuthRepo.countByCompetitionCompetId(
                    competition.getCompetId()
            );

            CreateCompetDetailDto competDetail = null;

            Matching sendMatching = matchingRepo.findByCompetitionCompetIdAndIsSender(competition.getCompetId(), IsSender.Y)
                    .orElseThrow(() -> new RuntimeException("보낸 사람의 도전장이 존재하지 않습니다."));

            Matching receiveMatching = matchingRepo.findByCompetitionCompetIdAndIsSender(competition.getCompetId(), IsSender.N)
                    .orElseThrow(() -> new RuntimeException("받은 사람의 도전장이 존재하지 않습니다."));

            // 보낸 사람 유저 카테고리
            UserCategory senderUserCategory =
                    userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(
                            sendMatching.getUser().getUserId(),
                            sendMatching.getCategory().getCategoryId()
                    ).orElseThrow(() -> new RuntimeException("보낸 사람의 유저카테고리가 존재하지 않습니다."));

            // 받은 사람 유저 카테고리
            UserCategory receiverUserCategory =
                    userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(
                            receiveMatching.getUser().getUserId(),
                            receiveMatching.getCategory().getCategoryId()
                    ).orElseThrow(() -> new RuntimeException("받은 사람의 유저카테고리가 존재하지 않습니다."));

            switch (imageAuthCount) { // if-else 보다 case가 더 알아보기 쉬워서 바꿈
                case 0: // 둘 다 인증하지 않은 경우
                    competDetail = new CreateCompetDetailDto(CompetResult.BOTH_FAIL);

                    // 양측 패배 횟수 ++
                    senderUserCategory.increaseFailCount();
                    receiverUserCategory.increaseFailCount();

                    break;
                case 1: // 둘 중 한 명만 인증한 경우
                    // 방법 1.
                    // 도전장을 경쟁아이디로 찾음
                    // 찾은 도전장 아이디를 사진인증에서 찾음
                    // 도전장 발신자여부가 Y이면 CompetResult를 send_WIN
                    ImageAuth imageAuth = imageAuthRepo.findByCompetitionCompetId(
                            competition.getCompetId()).orElseThrow(() -> new RuntimeException("경쟁에 해당하는 이미지 인증이 없습니다."));

                    // 해당 사진 인증이 가지고 있는 matchingId와 위에서 받아온 sender, receiver Matching을 가져와 비교 (IsSender로 비교도 가능)
                    if (imageAuth.getMatcing().getMatchingId() == sendMatching.getMatchingId()) {
                        competDetail = new CreateCompetDetailDto(CompetResult.SEND_WIN);
                        // 보낸 사람 승리, 받은 사람 패배
                        senderUserCategory.increaseWinCount();
                        receiverUserCategory.increaseFailCount();
                    } else if (imageAuth.getMatcing().getMatchingId() == receiveMatching.getMatchingId()) {
                        competDetail = new CreateCompetDetailDto(CompetResult.RECEIVE_WIN);
                        // 받은 사람 승리, 보낸 사람 패배
                        receiverUserCategory.increaseWinCount();
                        senderUserCategory.increaseFailCount();
                    }
                    break;
                case 2: // 둘 다 인증 한 경우 (이건 사진 인증에서 확인하므로 아마 여기까진 안올것)
                    competDetail = new CreateCompetDetailDto(CompetResult.BOTH_WIN);
                    // 양측 승리 횟수++
                    senderUserCategory.increaseWinCount();
                    receiverUserCategory.increaseWinCount();
                    break;
                default:
                    throw new RuntimeException("양측 사용자 인증 사진 총 합이 2장보다 많습니다.");
            }

            competition.updateCompetitionDetail(competDetailRepo.save(CreateCompetDetailDto.from(competDetail)));

            // 유저 카테고리 상태 업데이트
            senderUserCategory.updateCategoryStatus(CategoryStatus.NONE_STATUS);
            receiverUserCategory.updateCategoryStatus(CategoryStatus.NONE_STATUS);

            feedService.createFeed(competition);

        }

    }

}
