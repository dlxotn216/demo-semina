package app.livecoding.member.service;

import app.livecoding.base.interfaces.criteria.SearchCriteria;
import app.livecoding.member.domain.repository.MemberRepository;
import app.livecoding.member.interfaces.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 MemberSearchService 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component
public class MemberSearchService {

    private MemberRepository memberRepository;

    public MemberSearchService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Page<MemberDto.MemberSearchResponse> searchMembers(String searchOptionString, String condition, Pageable pageable) {
        //Criteria의 Field에 따라 동적으로 repository의 method를 선택할 수도 있으나 (join과 연관하여)
        //보통 criteria에 join 된 field로 검색한다면 결과도 조회되어야 하는 경우가 많으므로 분기하지 않음
        return this.memberRepository.findAllWithTeam(new SearchCriteria(searchOptionString, condition), pageable)
                .map(MemberDto::asMemberSearchResponse);
    }

    public MemberDto.MemberSearchResponse searchMemberByKey(Long memberKey) {
        return this.memberRepository.findById(memberKey)
                .map(MemberDto::asMemberSearchResponse)
                .orElseThrow(IllegalArgumentException::new);
    }
}
