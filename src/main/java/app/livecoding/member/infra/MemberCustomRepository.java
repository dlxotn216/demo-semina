package app.livecoding.member.infra;

import app.livecoding.base.interfaces.criteria.SearchCriteria;
import app.livecoding.member.domain.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by taesu on 2019-03-16.
 */
public interface MemberCustomRepository {
    Page<Member> findAllWithTeam(SearchCriteria searchCriteria, Pageable pageable);
}
