package app.livecoding.member.interfaces.controller;

import app.livecoding.member.interfaces.dto.MemberDto;
import app.livecoding.member.service.MemberCreateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 MemberCreateController 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController
public class MemberCreateController {

    private MemberCreateService memberCreateService;

    public MemberCreateController(MemberCreateService memberCreateService) {
        this.memberCreateService = memberCreateService;
    }

    @PostMapping("members")
    public ResponseEntity<MemberDto.MemberSearchResponse> createMember(
            @RequestBody MemberDto.MemberCreateOrUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.memberCreateService.createMember(request));
    }
}
