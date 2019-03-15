package app.livecoding.base.interfaces.criteria;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created by taesu on 2019-03-10.
 */
@Data
public class SearchCriteria {
    private static final String AT_STRING = "__AT__";
    private static final String DOT_STRING = "__DOT__";
    private static final String AT_CHAR = "@";
    private static final String DOT_CHAR = ".";

    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        if (value instanceof String) {
            value = ((String) value).replaceAll(AT_STRING, AT_CHAR)
                    .replaceAll(DOT_STRING, DOT_CHAR);
        }
        this.value = value;
    }

    public static String replaceAllToCompilable(String string) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }

        return string.replaceAll(AT_CHAR, AT_STRING)
                .replaceAll("\\" + DOT_CHAR, DOT_STRING);
    }
}
