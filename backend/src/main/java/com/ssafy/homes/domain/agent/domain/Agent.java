package com.ssafy.homes.domain.agent.domain;

import com.ssafy.homes.domain.estate.domain.DongCode;
import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.domain.member.domain.MemberStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agentId")
    private Long id;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    private String name;
    private String address;
    @Column(length = 20)
    private String email;
    @Column(length = 20)
    private String emailDomain;
    @Column(length = 20)
    private String password;
    @Column(length = 11)
    private String phone;
    private String img;


    @Builder
    private Agent(Long id, String name, String address, String email, String emailDomain, String password, String phone, String img) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.emailDomain = emailDomain;
        this.password = password;
        this.phone = phone;
        this.img = img;
    }

    public Agent updateAgent(String name, String address, String password, String phone, String img){
        this.name = name;
        this.address = address;
        this.password = password;
        this.phone = phone;
        this.img = img;
        return this;
    }
}
