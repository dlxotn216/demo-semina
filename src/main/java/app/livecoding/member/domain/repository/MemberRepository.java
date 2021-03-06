package app.livecoding.member.domain.repository;

import app.livecoding.member.domain.model.Member;
import app.livecoding.member.infra.MemberCustomRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 MemberRepository 인터페이스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public interface MemberRepository extends JpaRepository<Member, Long>,
                                          QuerydslPredicateExecutor<Member>,
                                          MemberCustomRepository,
                                          RevisionRepository<Member, Long, Integer> {

    Optional<Member> findByMemberId(@Param("memberId") String memberId);

    /**
     * BooleanExpressioin은 동작하지 않는다
     *
     * @param pageable          페이징 및 정렬
     * @param booleanExpression Where clues
     * @return 조회 결과
     */
    @Query(value = "select a from Member a left join fetch a.team",
            countQuery = "select COUNT(a.memberKey) from Member a inner join a.team")
    Page<Member> findAllWithTeam(Pageable pageable, Predicate booleanExpression);
}
