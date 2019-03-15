package app.livecoding.team.interfaces.controller;

import app.livecoding.team.interfaces.dto.TeamDto;
import app.livecoding.team.service.TeamCreateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 TeamCreateController 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController
public class TeamCreateController {
    private TeamCreateService teamCreateService;

    public TeamCreateController(TeamCreateService teamCreateService) {
        this.teamCreateService = teamCreateService;
    }

    @PostMapping("/teams")
    public ResponseEntity<TeamDto.TeamSearchResponse> createTeam(@RequestBody TeamDto.TeamCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.teamCreateService.createTeam(request));
    }
}
