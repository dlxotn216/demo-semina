package app.livecoding.member.service;

import app.livecoding.member.domain.model.Member;
import app.livecoding.member.domain.repository.MemberRepository;
import app.livecoding.member.interfaces.dto.MemberDto;
import org.springframework.stereotype.Component;

import static app.livecoding.member.interfaces.dto.MemberDto.asMemberSearchResponse;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 MemberCreateService 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component
public class MemberCreateService {
    private MemberRepository memberRepository;

    public MemberCreateService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto.MemberSearchResponse createMember(MemberDto.MemberCreateOrUpdateRequest request) {
        this.memberRepository.findByMemberId(request.getMemberId())
                .ifPresent(member -> {
                    throw new IllegalArgumentException(String.format("%s는 이미 존재하는 ID입니다", request.getMemberId()));
                });

        Member member = Member.builder().memberId(request.getMemberId())
                .memberName(request.getMemberName())
                .phone(request.getPhone()).build();
        return asMemberSearchResponse(this.memberRepository.save(member));

    }
}
