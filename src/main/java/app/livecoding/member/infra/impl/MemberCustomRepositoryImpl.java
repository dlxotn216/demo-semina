package app.livecoding.member.infra.impl;

import app.livecoding.base.interfaces.criteria.SearchCriteria;
import app.livecoding.member.domain.model.Member;
import app.livecoding.member.domain.model.QMember;
import app.livecoding.member.infra.MemberCustomRepository;
import app.livecoding.team.domain.model.QTeam;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static app.livecoding.member.infra.criteria.MemberPredicateBuilder.buildOrder;
import static app.livecoding.member.infra.criteria.MemberPredicateBuilder.buildPredicate;


/**
 * Created by taesu on 2019-03-16.
 */
public class MemberCustomRepositoryImpl extends QuerydslRepositorySupport implements MemberCustomRepository {

    public MemberCustomRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Page<Member> findAllWithTeam(SearchCriteria searchCriteria, Pageable pageable) {
        final String memberAlias = "m";
        final String teamAlias = "t";

        //MemberPredicateBuilder에서 지정한 PathBuilder에 variable과 동일하게 줄 것
        QMember qMember = new QMember(memberAlias);
        QTeam qTeam = new QTeam(teamAlias);

        JPQLQuery<Member> memberJPQLQuery
                = from(qMember).innerJoin(qMember.team, qTeam)
                .where(buildPredicate(searchCriteria, memberAlias, teamAlias)).fetchJoin();

        memberJPQLQuery.offset(pageable.getOffset());
        memberJPQLQuery.limit(pageable.getPageSize());

        memberJPQLQuery.orderBy(buildOrder(pageable, memberAlias, teamAlias));

        List<Member> fetch = memberJPQLQuery.fetch();
        return new PageImpl<>(fetch, pageable, memberJPQLQuery.fetchCount());
    }
}
