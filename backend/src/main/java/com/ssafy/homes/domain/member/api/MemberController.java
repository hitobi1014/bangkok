package com.ssafy.homes.domain.member.api;

import com.ssafy.homes.domain.common.CommonDto;
import com.ssafy.homes.domain.member.application.MemberLikeHomesService;
import com.ssafy.homes.domain.member.application.MemberService;
import com.ssafy.homes.domain.member.domain.MemberLikeHomes;
import com.ssafy.homes.domain.member.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberLikeHomesService likeHomesService;

    @PostMapping(value = "/join")
    public ResponseEntity<?> registMember(
//            @RequestBody RequestJoinMember member,
            @RequestPart(value = "member") RequestJoinMember member,
            @RequestPart(value = "file", required = false)MultipartFile file
            ) {
        try {
            log.debug("registMember....");

            log.info("받은 member값 : {}", member);
            log.info("받은 file : {}", file);

            memberService.saveMember(member, file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllMember() {
        try {
            log.debug("getAllMember....");
            List<ResponseMember> getMembers = memberService.getAllMember();
            return ResponseEntity.ok(getMembers);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    /**
     * 나중에 JWT 적용
     */
    @PostMapping(value = "/login")
//    public ResponseEntity<?> loginMember(@RequestBody RequestLoginMember member, HttpServletRequest request) {
    public ResponseEntity<?> loginMember(@RequestBody RequestLoginMember member) {
        try {
//            Member getMember = memberService.loginMember(member);
//            log.info("받은 member 값 : {}", member);
            ResponseMember getMember = memberService.loginMember(member);
//            if (getMember != null) {
//                request.getSession().setAttribute("memberInfo", getMember);
//            }
            return ResponseEntity.ok(getMember);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping(value = "/logout")
    public void logOutMember(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @PostMapping(value = "/regist/likehomes")
    public ResponseEntity<?> registLikeHomes(@RequestBody RequsetRegistLikeHome likeHome) {
        try{
            likeHomesService.registLikeHomes(likeHome);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @PostMapping(value = "/likehomes")
    public ResponseEntity<?> getLikeHomes (@RequestBody RequestLoginMember member) {
        try {
            List<ResponseLikeHomes> likeHomes = likeHomesService.getLikeHomes(member);
            return ResponseEntity.ok(CommonDto.of(likeHomes.size(), likeHomes));

        } catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @DeleteMapping(value = "/likehome/{id}")
    public ResponseEntity<?> deleteLikeHome(@PathVariable Long id) {
        try {
            likeHomesService.removeLikeHomes(id);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
