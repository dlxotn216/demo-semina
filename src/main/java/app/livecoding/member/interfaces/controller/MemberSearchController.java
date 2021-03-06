package app.livecoding.member.interfaces.controller;

import app.livecoding.member.interfaces.dto.MemberDto;
import app.livecoding.member.service.MemberSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by taesu at : 2019-03-15
 *
 * 여기에 MemberSearchController 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController
public class MemberSearchController {
    private MemberSearchService memberSearchService;

    public MemberSearchController(MemberSearchService memberSearchService) {
        this.memberSearchService = memberSearchService;
    }

    @GetMapping("/members")
    public ResponseEntity<Page<MemberDto.MemberSearchResponse>> searchMembers(
            @PageableDefault(sort = {"memberKey"}, direction = Sort.Direction.DESC, value = 5) Pageable pageable,
            @RequestParam(name = "criteria", required = false, defaultValue = "") String searchOptionString,
            @RequestParam(name = "condition", required = false, defaultValue = "and") String condition) {
        return ResponseEntity.ok(this.memberSearchService.searchMembers(searchOptionString, condition, pageable));
    }

    @GetMapping("/members/{memberKey}")
    public ResponseEntity<MemberDto.MemberSearchResponse> searchMemberByKey(@PathVariable("memberKey") Long memberKey) {
        return ResponseEntity.status(HttpStatus.OK).body(this.memberSearchService.searchMemberByKey(memberKey));
    }
}
