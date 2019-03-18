package app.livecoding.base.interfaces.criteria;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by taesu on 2019-03-10.
 */
@Data
@Slf4j
public class SearchOption {
    private static final String AT_STRING = "__AT__";
    private static final String DOT_STRING = "__DOT__";
    private static final String AT_CHAR = "@";
    private static final String DOT_CHAR = ".";

    private String key;
    private SearchOperation operation;
    private String value;

    public SearchOption(String key, SearchOperation operation, String value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
