package com.ssafy.sgdc.feed.service;


import com.ssafy.sgdc.badge.domain.Badge;
import com.ssafy.sgdc.competition.domain.Competition;
import com.ssafy.sgdc.competition.domain.ImageAuth;
import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.competition.repository.CompetDetailRepo;
import com.ssafy.sgdc.competition.repository.CompetitionRepo;
import com.ssafy.sgdc.competition.repository.ImageAuthRepo;
import com.ssafy.sgdc.competition.repository.MatchingRepo;
import com.ssafy.sgdc.enums.CompetResult;
import com.ssafy.sgdc.enums.IsSender;
import com.ssafy.sgdc.feed.repository.FeedLikeRepo;
import com.ssafy.sgdc.feed.repository.FeedRepo;
import com.ssafy.sgdc.feed.dto.FeedOneDto;
import com.ssafy.sgdc.feed.entity.Feed;
import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final FeedRepo feedRepo;
    private final CompetitionRepo competitionRepo;
    private final MatchingRepo matchingRepo;
    private final ImageAuthRepo imageAuthRepo;
    private final FeedLikeRepo feedLikeRepo;

    /**
     * 피드 생성
     */
    @Transactional
    public void createFeed(Competition compet) {
        Competition competition = findCompetitionById(compet.getCompetId());

        List<Matching> matches = findMatchesByCompetitionId(compet.getCompetId());
        Matching senderMatching = findMatchingByIsSender(matches, IsSender.Y); // 발신자 도전장
        Matching receiverMatching = findMatchingByIsSender(matches, IsSender.N); // 수신자 도전장

        validateMatchings(senderMatching, receiverMatching);
        if(competition.getCompetDetail().getCompetResult()==null) {
            throw new RuntimeException("경쟁 결과가 존재하지 않습니다.");
        }
        CompetResult result = competition.getCompetDetail().getCompetResult();

        if (result.equals(CompetResult.BOTH_FAIL)) {
            throw new RuntimeException("공동 패배하여 피드에 게시물이 올라가지 않습니다.");
        }

        String feedTitle = createFeedTitle(result, senderMatching, receiverMatching); // 타이틀 설정
        String feedImage = findFeedImage(result,senderMatching,receiverMatching); // 이미지 url 설정
        Feed feed = Feed.of(feedTitle, "", 0, 0, false, LocalDateTime.now(), null, feedImage, compet);
        feedRepo.save(feed);
    }


    /**
     * 특정 피드 불러오기
     */

    public FeedOneDto getFeedOne(int feedId, int userId) {
        // 피드 검색
        Feed feed = feedRepo.findByFeedId(feedId)
                .orElseThrow(() -> new RuntimeException("해당 피드를 찾을 수 없습니다."));

        // 매치 검색
        List<Matching> matches = findMatchesByCompetitionId(feed.getCompetId().getCompetId());
        Matching senderMatching = findSenderMatching(matches);
        Matching receiverMatching = findReceiverSenderMatching(matches);

        // 좋아요 여부 확인
        boolean isLiked = feedLikeRepo.existsByUser_UserIdAndFeed_FeedId(userId, feedId);

        // DTO 생성
        return buildFeedOneDto(feed, senderMatching, receiverMatching,isLiked);
    }

    private Matching findSenderMatching(List<Matching> matches) {
        for (Matching match : matches) {
            if (match.getIsSender() == IsSender.Y) {
                return match;
            }
        }
        throw new RuntimeException("송신자 매치를 찾을 수 없습니다.");
    }

    private Matching findReceiverSenderMatching(List<Matching> matches) {
        for (Matching match : matches) {
            if (match.getIsSender() == IsSender.N) {
                return match;
            }
        }
        throw new RuntimeException("수신자 매치를 찾을 수 없습니다.");
    }

    private FeedOneDto buildFeedOneDto(Feed feed, Matching senderMatching, Matching reveiverMatching, boolean isLiked) {
        User sender = senderMatching.getUser();
        Badge senderBadge = sender.getBadgeId();

        User receiver = reveiverMatching.getUser();
        Badge receiverBadge = receiver.getBadgeId();

        String senderBadgeImg = (senderBadge == null) ? "null" : senderBadge.getBadgeImg();
        String receiverBadgeImg = (receiverBadge == null) ? "null" : receiverBadge.getBadgeImg();

        return new FeedOneDto(
                feed.getFeedId(),
                feed.getFeedTitle(),
                feed.getFeedContent(),
                feed.getFeedLikeNum(),
                feed.getViews(),
                feed.isPrivacy(),
                feed.getCreateAt(),
                feed.getUpdateAt(),
                feed.getFeedImg(),
                sender.getUserId(),
                sender.getUserNickname(),
                sender.getUserImg(),
                senderBadgeImg,
                receiver.getUserId(),
                receiver.getUserNickname(),
                receiver.getUserImg(),
                receiverBadgeImg,
                isLiked
        );
    }


    /**
     *  피드 게시물 10개씩 불러오기
     */

    public Page<FeedOneDto> findItemsAfter(int feedId, int userId, Pageable pageable) {
        if (feedId==0) {
            // 처음 페이지를 로드할 때
            Page<Feed> feedPage = feedRepo.findAll(pageable);
            return feedPage.map(item -> convertToDto(item, userId));
        } else {
            // 커서 기반 페이지네이션: lastId 이후의 아이템을 조회
            Page<Feed> feedPage = feedRepo.findByFeedIdGreaterThan(feedId, pageable);
            return feedPage.map(item -> convertToDto(item, userId));
        }
    }


    /**
     * 피드 조회수 업데이트
     */
    @Transactional
    public int updateView(int feedId) {
        feedRepo.updateView(feedId);
        return getFeedViews(feedId);
    }

    private int getFeedViews(int feedId) {
        return feedRepo.findByFeedId(feedId)
                .map(Feed::getViews)
                .orElse(0);
    }


    private FeedOneDto convertToDto(Feed feed, int userId) {
        List<Matching> matches = findMatchesByCompetitionId(feed.getCompetId().getCompetId());
        Competition competition = findCompetitionById(feed.getCompetId().getCompetId());
        boolean isLiked = feedLikeRepo.existsByUser_UserIdAndFeed_FeedId(userId, feed.getFeedId());
        CompetResult result = competition.getCompetDetail().getCompetResult();
        Matching winnerMatching;
        Matching loserMatching;
        if (result.equals(CompetResult.SEND_WIN)) {
            winnerMatching = findMatchingByIsSender(matches, IsSender.Y);
            loserMatching = findMatchingByIsSender(matches, IsSender.N);
        }
        else if (result.equals(CompetResult.RECEIVE_WIN)){
            winnerMatching = findMatchingByIsSender(matches, IsSender.N);
            loserMatching = findMatchingByIsSender(matches, IsSender.Y);
        } else {
            winnerMatching = findMatchingByIsSender(matches, IsSender.Y);
            loserMatching = findMatchingByIsSender(matches, IsSender.N);
        }
        /**
         *
         *
         *
         *
         */

        User winner = winnerMatching.getUser();
        Badge winnerBadge = winner.getBadgeId();

        User loser = loserMatching.getUser();
        Badge loserBadge = loser.getBadgeId();

        String winnerBadgeImg = (winnerBadge == null) ? "null" : winnerBadge.getBadgeImg();
        String loserBadgeImg = (loserBadge == null) ? "null" : loserBadge.getBadgeImg();


        // Feed 엔티티의 데이터를 사용하여 FeedOneDto 객체를 생성합니다.
        return new FeedOneDto(
                feed.getFeedId(),
                feed.getFeedTitle(),
                feed.getFeedContent(),
                feed.getFeedLikeNum(),
                feed.getViews(),
                feed.isPrivacy(),
                feed.getCreateAt(),
                feed.getUpdateAt(),
                feed.getFeedImg(),
                // 매칭된 사용자 정보를 설정
                winner.getUserId(),
                winner.getUserNickname(),
                winner.getUserImg(),
                winnerBadgeImg,
                loser.getUserId(),
                loser.getUserNickname(),
                loser.getUserImg(),
                loserBadgeImg,
                isLiked
        );
    }

    private Matching findMatchingByIsSender(List<Matching> matches, IsSender isSender) {
        return matches.stream()
                .filter(match -> match.getIsSender().equals(isSender))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(isSender + "에 해당하는 Matching을 찾을 수 없습니다."));
    }


    private Competition findCompetitionById(int competId) {
        return competitionRepo.findByCompetId(competId)
                .orElseThrow(() -> new RuntimeException("해당 경쟁을 찾을 수 없습니다."));
    }

    private List<Matching> findMatchesByCompetitionId(int competId) {
        List<Matching> matches = matchingRepo.findByCompetitionCompetId(competId);
        if (matches.isEmpty()) {
            throw new RuntimeException("해당 경쟁의 도전장 결과를 찾을 수 없습니다.");
        }
        return matches;
    }

    private String findImageAuthByMatching(Matching matching) {
        return imageAuthRepo.findByMatcingMatchingId(matching.getMatchingId())
                .map(ImageAuth::getAuthImg)
                .orElse("");
    }

    private void validateMatchings(Matching senderMatching, Matching receiverMatching) {
        if (senderMatching == null || receiverMatching == null) {
            throw new RuntimeException("매칭 정보를 찾을 수 없습니다.");
        }
    }


    private String createFeedTitle(CompetResult result, Matching sender, Matching receiver) {
        String category = sender.getCategory().getCategoryName().toString();
        return switch (result) {
            case SEND_WIN -> String.format("[%s] %s님이 %s님과의 경쟁에서 이겼습니다!",
                    category, sender.getUser().getUserNickname(),
                    receiver.getUser().getUserNickname());
            case RECEIVE_WIN -> String.format("[%s] %s님이 %s님과의 경쟁에서 이겼습니다!",
                    category, receiver.getUser().getUserNickname(),
                    sender.getUser().getUserNickname());
            case BOTH_WIN -> String.format("%s님과 %s님이 공동승리 하였습니다!",
                    sender.getUser().getUserNickname(),
                    receiver.getUser().getUserNickname());
            default -> throw new RuntimeException("CompetResult이 정의되지 않았습니다.");
        };
    }

    private String findFeedImage(CompetResult result, Matching senderMatching, Matching receiverMatching) {
        return switch (result) {
            case SEND_WIN -> findImageAuthByMatching(senderMatching);
            case RECEIVE_WIN -> findImageAuthByMatching(receiverMatching);
            case BOTH_WIN -> findImageAuthByMatching(senderMatching); //// 일단 발신자 사진으로 feed 저장
            default -> "";
        };
    }






}
