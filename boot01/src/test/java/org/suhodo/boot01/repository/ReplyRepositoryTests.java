package org.suhodo.boot01.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page; // “댓글 목록”을 **몇 개씩 끊어서 불러오기** 위한 도구야.
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.suhodo.boot01.domain.Board;  // Board, Reply는 네가 만든 게시글과 댓글 데이터야.
import org.suhodo.boot01.domain.Reply;  // 게시글에 댓글을 달아야 하니까 이걸 불러와.

import lombok.extern.log4j.Log4j2;
// 이건 “기록기” 같은 거야.
// 테스트할 때 "지금 댓글 저장했어!" 같은 로그를 찍어주는 기능이야.

@SpringBootTest // 진짜 스프링처럼 테스트하겠다는 뜻이야. (웹 앱 실행처럼!)
@Log4j2         // 로그를 쓸 수 있게 해줘. log.info() 같은 거 쓸 수 있음.
public class ReplyRepositoryTests {

// @Autowired는 자동으로 연결해주는 코드야.
// 예를 들어 “댓글 저장소”랑 “댓글 테스트기”를 연결해줘.
    @Autowired
    private ReplyRepository replyRepository;
// ReplyRepository는 댓글을 저장하거나 불러오는 기능을 담당해.
// 이걸 자동으로 연결해주는 거야.
// “댓글 저장소를 테스트기에 연결해주세요~” 이런 느낌.

// @Test는 “이건 테스트용 코드야” 하고 표시해주는 도장 같은 거야.
// 이걸 붙이면 스프링이 "아~ 실행해봐야겠네!" 하고 테스트해줘.
    @Test
    public void testInsert(){
        Long bno = 100L;  // 게시글 번호 (100번 글에 댓글을 달 거야)

    // 100번 게시글 객체 만들기 (진짜 DB에서 가져오진 않고, 껍데기만 만듦)
        Board board = Board.builder().bno(bno).build();

    // 댓글 하나 만들기: 누구? replyer1이 뭐라고? "댓글......"
        Reply reply = Reply.builder()
                        .board(board) // 어떤 게시글에 달릴 댓글인지 연결
                        .replyText("댓글......")
                        .replyer("replyer1")
                        .build();

        replyRepository.save(reply); // 댓글 저장!
    }

    @Test
    public void testBoardReplies(){
        Long bno = 100L; // 100번 게시글에 달린 댓글을 볼 거야

        
    // 1페이지에 10개씩 댓글을 불러오자! 최신순으로 정렬!
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

    // 댓글 목록 불러오기 (replyRepository에서 직접 불러오는 기능 사용)
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

    // 불러온 댓글들 하나씩 출력해보자!
        result.getContent().forEach(reply -> {
            log.info(reply);
        });
    }
}
