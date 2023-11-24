package com.ssafy.homes.domain.member.dto;

import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.domain.member.domain.MemberStatus;
import com.ssafy.homes.global.common.file.File;
import com.ssafy.homes.global.common.file.FileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

//@Getter
//@NoArgsConstructor
@Data
@NoArgsConstructor
public class RequestJoinMember {
    private String email;
    private String emailDomain;
    private String password;
    private String name;
    private String mobile;
//    private String img;
    private FileDto memberFile;
//    private MultipartFile file;
    private MemberStatus status;

//    private MultipartFile file = new MultipartFile;

    public RequestJoinMember(String email, String emailDomain, String password, String name, String mobile, FileDto memberFile) {
        this.email = email;
        this.emailDomain = emailDomain;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
        this.memberFile = memberFile;
        this.status = MemberStatus.ACTIVE; // 새로 가입하는 회원은 항상 ACTIVE 상태
    }

    public Member toEntity(RequestJoinMember joinMember) {
//        MultipartFile getFile = joinMember.getFile();
        File file = joinMember.getMemberFile()
                .toEntity();
//        File fileEntity = FileDto.builder()
//                .originFileName(getFile.getOriginalFilename())
//                .fullPath(uploadDir + getFile.getOriginalFilename())
//                .build()
//                .toEntity();

        return Member.builder()
                .email(joinMember.getEmail())
                .emailDomain(joinMember.getEmailDomain())
                .password(joinMember.getPassword())
                .name(joinMember.getName())
                .mobile(joinMember.getMobile())
//                .img(joinMember.getImg())
                .file(file)
                .status(joinMember.getStatus())
                .build();
    }
}
