package com.ssafy.sgdc.feed;


import com.ssafy.sgdc.competition.domain.CompetDetail;
import com.ssafy.sgdc.competition.domain.Competition;
import com.ssafy.sgdc.competition.domain.ImageAuth;
import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.competition.repository.CompetDetailRepo;
import com.ssafy.sgdc.competition.repository.CompetitionRepo;
import com.ssafy.sgdc.competition.repository.ImageAuthRepo;
import com.ssafy.sgdc.competition.repository.MatchingRepo;
import com.ssafy.sgdc.enums.CompetResult;
import com.ssafy.sgdc.enums.IsSender;
import com.ssafy.sgdc.feed.dto.FeedOneDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final CompetDetailRepo competDetailRepo;
    private final MatchingRepo matchingRepo;
    private final ImageAuthRepo imageAuthRepo;

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
        CompetResult result = competition.getCompetDetail().getCompetResult();

        String feedTitle = createFeedTitle(result, senderMatching, receiverMatching); // 타이틀 설정
        String feedImage = findFeedImage(result,senderMatching,receiverMatching); // 이미지 url 설정
        Feed feed = Feed.of(feedTitle, "", 0, 0, false, LocalDateTime.now(), null, feedImage, compet);
        feedRepo.save(feed);
    }


    /**
     * 특정 피드 불러오기
     */

    public FeedOneDto getFeedOne(int feedId) {
        Feed feed = feedRepo.findByFeedId(feedId)
                .orElseThrow(() -> new RuntimeException("해당 피드를 찾을 수 없습니다."));

        List<Matching> matches = findMatchesByCompetitionId(feed.getCompetId().getCompetId());
        Matching senderMatching = findMatchingByIsSender(matches, IsSender.Y);

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
                senderMatching.getUser().getUserId(),
                senderMatching.getUser().getUserNickname(),
                senderMatching.getUser().getUserImg()
        );
    }


    /**
     *  피드 게시물 10개씩 불러오기
     */

    public Page<FeedOneDto> findItemsAfter(int feedId, Pageable pageable) {
        if (feedId==0) {
            // 처음 페이지를 로드할 때
            Page<Feed> feedPage = feedRepo.findAll(pageable);
            return feedPage.map(this::convertToDto);
        } else {
            // 커서 기반 페이지네이션: lastId 이후의 아이템을 조회
            Page<Feed> feedPage = feedRepo.findByFeedIdGreaterThan(feedId, pageable);
            return feedPage.map(this::convertToDto);
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


    private FeedOneDto convertToDto(Feed feed) {
        List<Matching> matches = findMatchesByCompetitionId(feed.getCompetId().getCompetId());
        Matching senderMatching = findMatchingByIsSender(matches, IsSender.Y);

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
                senderMatching.getUser().getUserId(),
                senderMatching.getUser().getUserNickname(),
                senderMatching.getUser().getUserImg()
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
//      String category = sender.getCategory().getCategoryName().toString();
        String category = "test";
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
            case BOTH_FAIL -> String.format("%s님과 %s님이 공동배패 하였습니다.",
                    sender.getUser().getUserNickname(),
                    receiver.getUser().getUserNickname());
            default -> throw new RuntimeException("CompetResult이 정의되지 않았습니다.");
        };
    }

    private String findFeedImage(CompetResult result, Matching senderMatching, Matching receiverMatching) {
        return switch (result) {
            case SEND_WIN -> findImageAuthByMatching(senderMatching);
            case RECEIVE_WIN -> findImageAuthByMatching(receiverMatching);
            default -> "";
        };
    }






}
