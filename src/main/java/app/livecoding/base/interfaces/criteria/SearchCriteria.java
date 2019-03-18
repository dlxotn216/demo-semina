package app.livecoding.base.interfaces.criteria;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by taesu at : 2019-03-18
 *
 * 여기에 SearchCriteria 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Data
@Slf4j
public class SearchCriteria {
    private List<SearchOption> options;
    private String condition;


    public SearchCriteria(String searchOptionString, String condition) {
        this.options = new ArrayList<>();
        this.condition = condition;
        if (StringUtils.isEmpty(searchOptionString)) {
            return;
        }

        Pattern pattern = Pattern.compile("(.+?)(=|!=|:|<|>|<=|>=)(.*?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchOptionString + ",");
        while (matcher.find()) {
            try {
                options.add(new SearchOption(matcher.group(1),
                                             SearchOperation.fromString(matcher.group(2)),
                                             matcher.group(3)));
            } catch (UnsupportedOperationException e) {
                //Ignore...
                log.warn("{} 에러로 인해 특정 criteria가 무시됩니다", e.getMessage(), e);
            }
        }
    }
}
