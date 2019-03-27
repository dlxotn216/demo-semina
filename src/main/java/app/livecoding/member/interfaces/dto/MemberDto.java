package app.livecoding.member.interfaces.dto;

import app.livecoding.member.domain.model.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 MemberDto 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public final class MemberDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberSearchResponse {
        private Long memberKey;
        private String memberId;
        private String memberName;
        private String email;
        private String phone;
        private String teamName;
        private LocalDate startedAt;
    }

    public static MemberSearchResponse asMemberSearchResponse(Member member) {
        return new MemberSearchResponse(member.getMemberKey(), member.getMemberId(), member.getMemberName(), member.getEmail(),
                                        member.getPhone(), member.getTeam() != null ? member.getTeam().getTeamName() : "",
                                        member.getStartedAt());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberCreateOrUpdateRequest {
        private String memberId;
        private String memberName;
        private String phone;
        private String email;
    }
}
