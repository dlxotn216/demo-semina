package app.livecoding.member.service;

import app.livecoding.member.domain.repository.MemberRepository;
import app.livecoding.member.interfaces.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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

    public Page<MemberDto.MemberSearchResponse> searchMembers(Pageable pageable) {
        return this.memberRepository.findAllWithTeam(pageable)
                .map(MemberDto::asMemberSearchResponse);
    }

    public MemberDto.MemberSearchResponse searchMemberByKey(Long memberKey) {
        return this.memberRepository.findById(memberKey)
                .map(MemberDto::asMemberSearchResponse)
                .orElseThrow(IllegalArgumentException::new);
    }
}
