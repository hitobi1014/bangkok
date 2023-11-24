package com.ssafy.homes.domain.agent.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.querydsl.core.types.EntityPath;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String date;
    private String time;

    @ManyToOne(optional = true)
    @JoinColumn(name = "agentId")
    private Agent agent;

    @Builder
    private Appointment(Long no, String date, String time, Agent agent) {
        this.no = no;
        this.date = date;
        this.time = time;
        this.agent = agent;
    }
}
