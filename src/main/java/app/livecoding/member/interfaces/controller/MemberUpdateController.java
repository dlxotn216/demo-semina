package app.livecoding.member.interfaces.controller;

import app.livecoding.member.interfaces.dto.MemberDto;
import app.livecoding.member.service.MemberUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 MemberUpdateController 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController
public class MemberUpdateController {
    private MemberUpdateService memberUpdateService;

    public MemberUpdateController(MemberUpdateService memberUpdateService) {
        this.memberUpdateService = memberUpdateService;
    }

    @PutMapping("/members/{memberKey}")
    public ResponseEntity<MemberDto.MemberSearchResponse> updateMember(@PathVariable("memberKey") Long memberKey,
                                                                       @RequestBody MemberDto.MemberCreateOrUpdateRequest request) {
        Objects.requireNonNull(memberKey, "memberKey가 존재해야합니다");
        
        return ResponseEntity.status(HttpStatus.OK).body(this.memberUpdateService.updateMember(memberKey, request));
    }
}
