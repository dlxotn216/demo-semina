package app.livecoding.member.infra.impl;

import app.livecoding.member.domain.model.Member;
import app.livecoding.member.domain.model.QMember;
import app.livecoding.member.infra.MemberCustomRepository;
import app.livecoding.team.domain.model.QTeam;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


/**
 * Created by taesu on 2019-03-16.
 */
public class MemberCustomRepositoryImpl extends QuerydslRepositorySupport implements MemberCustomRepository {

    public MemberCustomRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Page<Member> findAllWithTeam(BooleanExpression predicate, Pageable pageable) {
        //MemberPredicateBuilder에서 지정한 PathBuilder에 variable과 동일하게 줄 것
        QMember qMember = new QMember("m");
        QTeam qTeam = new QTeam("t");

        JPQLQuery<Member> memberJPQLQuery = from(qMember).innerJoin(qMember.team, qTeam).where(predicate).fetchJoin();

        memberJPQLQuery.offset(pageable.getOffset());
        memberJPQLQuery.limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            memberJPQLQuery.orderBy(new OrderSpecifier(Order.valueOf(o.getDirection().name()),
                    new PathBuilder<>(Member.class, "m")));
        }

        List<Member> fetch = memberJPQLQuery.fetch();
        return new PageImpl<>(fetch, pageable, memberJPQLQuery.fetchCount());
    }
}
