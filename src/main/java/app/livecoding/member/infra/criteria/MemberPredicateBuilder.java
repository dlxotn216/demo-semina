package app.livecoding.member.infra.criteria;


import app.livecoding.base.interfaces.criteria.SearchCriteria;
import app.livecoding.base.interfaces.criteria.SearchOption;
import app.livecoding.member.domain.model.Member;
import app.livecoding.team.domain.model.Team;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static app.livecoding.base.interfaces.criteria.SearchOperation.getBooleanExpression;

/**
 * Created by taesu on 2019-03-16.
 */
@Slf4j
public final class MemberPredicateBuilder {

    private MemberPredicateBuilder() {
    }

    private static final Map<String, Class> PROPERTY_OWNER_MAP = new HashMap<>();
    private static final Map<String, Class> PROPERTY_TYPE_MAP = new HashMap<>();

    static {
        PROPERTY_OWNER_MAP.put("teamName", Team.class);
        PROPERTY_TYPE_MAP.put("startedAt", LocalDate.class);
    }

    public static BooleanExpression buildPredicate(SearchCriteria searchCriteria,
                                                   String memberAlias, String teamAlias) {
        if (CollectionUtils.isEmpty(searchCriteria.getOptions())) {
            return null;
        }

        return searchCriteria.getOptions().stream()
                .map(criteria -> with(criteria, memberAlias, teamAlias))
                .filter(Objects::nonNull)
                .reduce(searchCriteria.getCondition().equalsIgnoreCase("and")
                                ? Expressions.asBoolean(true).isTrue()
                                : Expressions.asBoolean(false).isTrue(),
                        searchCriteria.getCondition().equalsIgnoreCase("and")
                                ? BooleanExpression::and
                                : BooleanExpression::or);
    }

    private static BooleanExpression with(SearchOption searchCriteria, String memberAlias, String teamAlias) {
        PathBuilder<?> entityPath;
        if (PROPERTY_OWNER_MAP.getOrDefault(searchCriteria.getKey(), Member.class) == Team.class) {
            entityPath = new PathBuilder<>(Team.class, teamAlias);
        } else {
            entityPath = new PathBuilder<>(Member.class, memberAlias);
        }

        final Class propertyType = PROPERTY_TYPE_MAP.getOrDefault(searchCriteria.getKey(), String.class);
        if (propertyType == Long.class) {
            return getBooleanExpression(entityPath.getNumber(searchCriteria.getKey(), Long.class),
                                        searchCriteria.getOperation(),
                                        Long.valueOf(searchCriteria.getValue()));
        } else if (propertyType == Double.class) {
            return getBooleanExpression(entityPath.getNumber(searchCriteria.getKey(), Double.class),
                                        searchCriteria.getOperation(),
                                        Double.valueOf(searchCriteria.getValue()));
        } else if (propertyType == Integer.class) {
            return getBooleanExpression(entityPath.getNumber(searchCriteria.getKey(), Integer.class),
                                        searchCriteria.getOperation(),
                                        Integer.valueOf(searchCriteria.getValue()));
        } else if (propertyType == LocalDate.class) {
            return getBooleanExpression(entityPath.getDate(searchCriteria.getKey(), LocalDate.class),
                                        searchCriteria.getOperation(),
                                        LocalDate.parse(searchCriteria.getValue()));
        } else if (propertyType == String.class) {
            return getBooleanExpression(entityPath.getString(searchCriteria.getKey()),
                                        searchCriteria.getOperation(),
                                        searchCriteria.getValue());
        }

        return null;
    }

    public static OrderSpecifier[] buildOrder(Pageable pageable, String memberAlias, String teamAlias) {
        return pageable.getSort().stream()
                .map(order -> {
                    OrderSpecifier orderSpecifier;
                    if (PROPERTY_OWNER_MAP.getOrDefault(order.getProperty(), Member.class) == Team.class) {
                        orderSpecifier = new OrderSpecifier(Order.valueOf(order.getDirection().name()),
                                                            new PathBuilder<>(Team.class, teamAlias));
                    } else {
                        orderSpecifier = new OrderSpecifier(Order.valueOf(order.getDirection().name()),
                                                            new PathBuilder<>(Member.class, memberAlias));
                    }
                    return orderSpecifier;
                })
                .toArray(OrderSpecifier[]::new);
    }
}
