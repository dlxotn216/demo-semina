package app.livecoding.member.interfaces.criteria;


import app.livecoding.member.domain.model.Member;
import com.querydsl.core.types.dsl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taesu on 2019-03-16.
 */
public class MemberPredicateBuilder {
    private List<BooleanExpression> criterias = new ArrayList<>();

    public void with(String property, String operator, String value) {
        PathBuilder<Member> entityPath = new PathBuilder<>(Member.class, "m");
        if (isNumberValue(value)) {
            NumberPath<Double> path = entityPath.getNumber(property, Double.class);
            switch (operator) {
                case "=":
                    criterias.add(path.eq(Double.parseDouble(value)));
                    return;
                case ">":
                    criterias.add(path.gt(Double.parseDouble(value)));
                    return;
                case ">=":
                    criterias.add(path.goe(Double.parseDouble(value)));
                    return;
                case "<":
                    criterias.add(path.lt(Double.parseDouble(value)));
                    return;
                case "<=":
                    criterias.add(path.loe(Double.parseDouble(value)));
                    return;
            }

        } else {
            StringPath path = entityPath.getString(property);
            switch (operator) {
                case "=":
                    criterias.add(path.equalsIgnoreCase(value));
                    return;
                case ":":
                    criterias.add(path.containsIgnoreCase(value));
                    return;
            }
        }

    }

    private Boolean isNumberValue(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public BooleanExpression build(String condition) {
        if (this.criterias.size() == 0) {
            return null;
        }

        BooleanExpression result;
        if (condition.equalsIgnoreCase("and")) {
            result = Expressions.asBoolean(true).isTrue();
        } else {
            result = Expressions.asBoolean(false).isTrue();
        }

        for (BooleanExpression predicate : this.criterias) {
            result = condition.equalsIgnoreCase("and") ? result.and(predicate) : result.or(predicate);
        }
        return result;
    }


}
