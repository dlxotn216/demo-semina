package app.livecoding.member.interfaces.criteria;


import app.livecoding.base.interfaces.criteria.SearchCriteria;
import app.livecoding.base.interfaces.criteria.SearchOperation;
import app.livecoding.member.domain.model.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static app.livecoding.base.interfaces.criteria.SearchOperation.getBooleanExpression;

/**
 * Created by taesu on 2019-03-16.
 */
@Slf4j
public class MemberPredicateBuilder {
    private List<BooleanExpression> criterias = new ArrayList<>();
    private String condition;

    private static Map<String, Class> propertyTypeMap = new HashMap<>();

    static {
        propertyTypeMap.put("startedAt", LocalDate.class);
    }

    public MemberPredicateBuilder(String criteria, String condition) {
        this.condition = condition;
        if (StringUtils.isEmpty(criteria)) {
            return;
        }

        Pattern pattern = Pattern.compile("(.+?)(=|!=|:|<|>|<=|>=)(.*?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(criteria + ",");
        while (matcher.find()) {
            try {
                this.with(matcher.group(1), SearchOperation.fromString(matcher.group(2)), matcher.group(3));
            } catch (UnsupportedOperationException e) {
                //Ignore...
                log.warn("{} 에러로 인해 특정 criteria가 무시됩니다", e.getMessage(), e);
            }
        }
    }

    private void with(String property, SearchOperation operation, String value) {
        PathBuilder<Member> entityPath = new PathBuilder<>(Member.class, "m");
        Class propertyType = propertyTypeMap.getOrDefault(property, String.class);

        if (propertyType == Long.class) {
            this.criterias.add(
                    getBooleanExpression(entityPath.getNumber(property, Long.class), operation, Long.valueOf(value)));
        } else if (propertyType == Double.class) {
            this.criterias.add(
                    getBooleanExpression(entityPath.getNumber(property, Double.class), operation, Double.valueOf(value)));
        } else if (propertyType == Integer.class) {
            this.criterias.add(
                    getBooleanExpression(entityPath.getNumber(property, Integer.class), operation, Integer.valueOf(value)));
        } else if (propertyType == LocalDate.class) {
            this.criterias.add(
                    getBooleanExpression(entityPath.getDate(property, LocalDate.class), operation, LocalDate.parse(value)));
        } else if (propertyType == String.class) {
            this.criterias.add(
                    getBooleanExpression(entityPath.getString(property), operation, value));
        }
    }

    public BooleanExpression build() {
        if (CollectionUtils.isEmpty(this.criterias)) {
            return null;
        }

        return this.criterias.stream()
                .reduce(condition.equalsIgnoreCase("and")
                                ? Expressions.asBoolean(true).isTrue()
                                : Expressions.asBoolean(false).isTrue(),
                        condition.equalsIgnoreCase("and")
                                ? BooleanExpression::and
                                : BooleanExpression::or);
    }


}
