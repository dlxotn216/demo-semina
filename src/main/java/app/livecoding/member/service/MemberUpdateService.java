package app.livecoding.member.service;

import app.livecoding.member.domain.model.Member;
import app.livecoding.member.domain.repository.MemberRepository;
import app.livecoding.member.interfaces.dto.MemberDto;
import org.springframework.stereotype.Component;

import static app.livecoding.member.interfaces.dto.MemberDto.asMemberSearchResponse;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 MemberUpdateService 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component
public class MemberUpdateService {
    private MemberRepository memberRepository;

    public MemberUpdateService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto.MemberSearchResponse updateMember(Long memberKey, MemberDto.MemberCreateOrUpdateRequest request) {
        Member member = this.memberRepository.findById(memberKey)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));


        return asMemberSearchResponse(this.memberRepository.save(member.updateMember(request.getMemberName(), 
                                                                                     request.getPhone())));
    }
}
