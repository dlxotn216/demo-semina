package app.livecoding.team.interfaces.dto;

import app.livecoding.member.interfaces.dto.MemberDto;
import app.livecoding.team.domain.model.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 TeamDto 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public class TeamDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamSearchResponse {
        private String teamName;
        private List<MemberDto.MemberSearchResponse> members;
    }

    public static TeamSearchResponse asTeamSearchResponse(Team team) {
        return new TeamSearchResponse(team.getTeamName(), team.getMembers().stream().map(MemberDto::asMemberSearchResponse).collect(Collectors.toList()));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamCreateRequest {
        private String teamName;
        private List<MemberDto.MemberCreateOrUpdateRequest> members;

        public List<MemberDto.MemberCreateOrUpdateRequest> getMembers() {
            return this.members == null ? Collections.emptyList() : this.members;
        }
    }

}
