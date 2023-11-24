package com.ssafy.homes.domain.member.domain;

import com.ssafy.homes.global.common.BaseTimeEntity;
import com.ssafy.homes.global.common.file.File;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String email;
    @Column(length = 20)
    private String emailDomain;
    @Column(length = 20)
    private String password;
    @Column(length = 20)
    private String name;
    @Column(length = 11)
    private String mobile;
//    private String img; // 경로활용? 찾아보기

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fileId")
    private File file;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    private Member(String email, String emailDomain, String password, String name, String mobile, File file, MemberStatus status) {
        this.email = email;
        this.emailDomain = emailDomain;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
//        this.img = img;
        this.file = file;
        this.status = status;
    }

    public Member updateMember(String password, String name, String mobile, File file){
        this.password = password;
        this.name = name;
        this.mobile = mobile;
//        this.img = img;
        this.file = file;
        return this;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
