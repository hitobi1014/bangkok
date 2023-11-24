package com.ssafy.homes.domain.member.dto;

import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.domain.member.domain.MemberStatus;
import com.ssafy.homes.global.common.file.File;
import com.ssafy.homes.global.common.file.FileDto;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class ResponseMember {

    private String email;
    private String emailDomain;
    private String password;
    private String name;
    private String mobile;
    private MemberStatus status;
    private FileDto fileDto; // 회원 이미지 저장을 위한 DTO

    public FileDto toFileDto(File file) {
        return FileDto.builder()
                .id(file.getId())
                .originFileName(file.getOriginFileName())
                .fullPath(file.getFullPath())
                .saveFileName(file.getSaveFileName())
                .realPath(file.getRealPath())
                .build();
    }

    public ResponseMember toDto(Member member) {
        email = member.getEmail();
        emailDomain = member.getEmailDomain();
        password = member.getPassword();
        name = member.getName();
        mobile = member.getMobile();
        status = member.getStatus();

        if (member.getFile() != null) {
            fileDto = toFileDto(member.getFile());
        }

        return this;
    }
}
