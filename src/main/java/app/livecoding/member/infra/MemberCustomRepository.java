package app.livecoding.member.infra;

import app.livecoding.member.domain.model.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.function.Predicate;

/**
 * Created by taesu on 2019-03-16.
 */
public interface MemberCustomRepository {
    Page<Member> findAllWithTeam(BooleanExpression predicate, Pageable pageable);
}
