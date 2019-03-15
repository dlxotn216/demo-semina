package app.livecoding.team.service;

import app.livecoding.member.domain.model.Member;
import app.livecoding.team.domain.model.Team;
import app.livecoding.team.domain.repository.TeamRepository;
import app.livecoding.team.interfaces.dto.TeamDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static app.livecoding.team.interfaces.dto.TeamDto.asTeamSearchResponse;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 TeamCreateService 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component
public class TeamCreateService {
    private TeamRepository teamRepository;

    public TeamCreateService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public TeamDto.TeamSearchResponse createTeam(TeamDto.TeamCreateRequest request) {
        final Team save = Team.builder().teamName(request.getTeamName()).build();

        request.getMembers().stream()
                .map(member -> Member.builder().memberId(member.getMemberId())
                        .memberName(member.getMemberName()).phone(member.getPhone()).team(save).build())
                .forEach(save::addMember);

        return asTeamSearchResponse(this.teamRepository.save(save));
    }
}
