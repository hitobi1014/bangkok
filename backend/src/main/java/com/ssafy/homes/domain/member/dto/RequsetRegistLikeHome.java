package com.ssafy.homes.domain.member.dto;

import com.ssafy.homes.domain.estate.domain.EstateSales;
import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.domain.member.domain.MemberLikeHomes;
import lombok.Data;

@Data
public class RequsetRegistLikeHome {

    private String email;
    private String emailDomain;
    private Long estateId;

    public MemberLikeHomes toEntity(Member member, EstateSales sales) {
        return MemberLikeHomes.builder()
                .member(member)
                .estateSales(sales)
                .build();
    }
}
