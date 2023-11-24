package com.ssafy.homes.domain.member.domain;

import com.ssafy.homes.domain.estate.domain.EstateSales;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "LikeHomes" )
public class MemberLikeHomes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "memberId")
    private Member member;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estateSalesId")
    private EstateSales estateSales;

    @Builder
    private MemberLikeHomes(Member member, EstateSales estateSales) {
        this.member = member;
        this.estateSales = estateSales;
    }
}
