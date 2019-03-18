package app.livecoding.base.interfaces.criteria;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * Created by taesu at : 2019-03-18
 *
 * 여기에 SearchOperation 열거형에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum SearchOperation {

    EQUAL("="),
    NOT_EQUAL("!="),
    LIKE(":"),
    LESS_THEN("<"),
    LESS_OR_EQUAL_THEN("<="),
    GRATER_THEN(">"),
    GRATER_OR_EQUAL_THEN(">=");

    private String operationString;

    public static SearchOperation fromString(String operationString) {
        return Stream.of(values())
                .filter(searchOperation -> searchOperation.operationString.equalsIgnoreCase(operationString))
                .findAny()
                .orElseThrow(() -> new UnsupportedOperationException(operationString + "은 지원하지 않는 연산자입니다"));
    }

    public static <A extends Number & Comparable<?>> BooleanExpression getBooleanExpression(NumberPath<A> path,
                                                                                            SearchOperation operation,
                                                                                            A value) {
        switch (operation) {
            case EQUAL:
                return path.eq(value);
            case GRATER_THEN:
                return path.gt(value);
            case GRATER_OR_EQUAL_THEN:
                return path.goe(value);
            case LESS_THEN:
                return path.lt(value);
            case LESS_OR_EQUAL_THEN:
                return path.loe(value);
            default:
                return null;
        }
    }

    public static BooleanExpression getBooleanExpression(StringPath path,
                                                         SearchOperation operation,
                                                         String value) {
        switch (operation) {
            case EQUAL:
                return path.equalsIgnoreCase(value);
            case LIKE:
                return path.containsIgnoreCase(value);
            default:
                return null;
        }
    }

    public static BooleanExpression getBooleanExpression(DatePath<LocalDate> path,
                                                         SearchOperation operation,
                                                         LocalDate value) {
        switch (operation) {
            case EQUAL:
                return path.eq(value);
            case GRATER_THEN:
                return path.gt(value);
            case GRATER_OR_EQUAL_THEN:
                return path.goe(value);
            case LESS_THEN:
                return path.lt(value);
            case LESS_OR_EQUAL_THEN:
                return path.loe(value);
            default:
                return null;
        }
    }
}
