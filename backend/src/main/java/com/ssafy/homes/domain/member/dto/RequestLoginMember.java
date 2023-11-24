package com.ssafy.homes.domain.member.dto;

import com.ssafy.homes.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLoginMember {
    private String email;
    private String emailDomain;
    private String password;

    public Member toEntity(RequestLoginMember member) {
        return Member.builder()
                .email(member.getEmail())
                .emailDomain(member.getEmailDomain())
                .password(member.getPassword())
                .build();
    }
}
