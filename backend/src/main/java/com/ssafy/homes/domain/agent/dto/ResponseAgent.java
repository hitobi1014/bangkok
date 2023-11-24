package com.ssafy.homes.domain.agent.dto;


import com.ssafy.homes.domain.agent.domain.Agent;
import com.ssafy.homes.domain.agent.domain.Appointment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@ToString
public class ResponseAgent {

    private Long id;
    private String name;
    private List<ResponseAppointment> appointments = new ArrayList<>();
    private String address;
    private String email;
    private String emailDomain;
    private String password;
    private String phone;
    private String img;

    @Builder
    private ResponseAgent(String name, String address, String email, String emailDomain, String password, String phone, String img) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.emailDomain = emailDomain;
        this.password = password;
        this.phone = phone;
        this.img = img;
    }

    public Agent toEntity(ResponseAgent agent) {
        return Agent.builder()
                .id(agent.getId())
                .name(agent.getName())
                .address(agent.getAddress())
                .email(agent.getEmail())
                .emailDomain(agent.getEmailDomain())
                .password(agent.getPassword())
                .phone(agent.getPhone())
                .img(agent.getImg())
                .build();
    }

    public ResponseAgent toDto(Agent agent) {
        id = agent.getId();
        name = agent.getName();
        address = agent.getAddress();
        email = agent.getEmail();
        emailDomain = agent.getEmailDomain();
        password = agent.getPassword();
        phone = agent.getPhone();
        img = agent.getImg();
        return this;
    }
}
