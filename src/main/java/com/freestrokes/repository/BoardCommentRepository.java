package com.freestrokes.repository;

import com.freestrokes.domain.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment, String> {

    // TODO: @EntityGraph 어노테이션
    // 즉시로딩(EAGER) 방식으로 매핑된 연관관계의 객체를 조회할 때도 사용 가능.
    // left outer join으로 읽어오기 때문에 LazyInitializtionException 해결 가능
    // but, 부모 엔티티를 중복 조회하여 권장되지 않음. (이 경우에 대한 distinct 설정을 지원)

    // TODO: CASE2) repository 메서드에 @EntityGraph 사용하여 연관 객체 조회
//    @EntityGraph(
//        attributePaths = {"board"},
//        type = EntityGraph.EntityGraphType.FETCH
//    )
//    Optional<BoardComment> findByBoardCommentId(String boardCommentId);

}
