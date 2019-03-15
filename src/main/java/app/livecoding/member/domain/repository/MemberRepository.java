package app.livecoding.member.domain.repository;

import app.livecoding.member.domain.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(@Param("memberId") String memberId);

    @Query(value = "select a from Member a left join fetch a.team",
            countQuery = "select COUNT(a.memberKey) from Member a inner join a.team")
    Page<Member> findAllWithTeam(Pageable pageable);
}
