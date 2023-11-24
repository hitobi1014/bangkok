package com.ssafy.homes.domain.agent.dto;

import com.ssafy.homes.domain.agent.domain.Agent;
import com.ssafy.homes.domain.agent.domain.Appointment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class RequestAppointment {

    private Long no;
    private String date;
    private String time;
    private Agent agent;

    @Builder
    public RequestAppointment(String date, String time, Agent agent) {
        this.date = date;
        this.time = time;
        this.agent = agent;
    }

    public Appointment toEntity(RequestAppointment appointment) {
        return Appointment.builder()
                .no(appointment.getNo())
                .date(appointment.getDate())
                .time(appointment.getTime())
                .agent(appointment.getAgent())
                .build();
    }

    public RequestAppointment toDto(Appointment appointment) {
        no = appointment.getNo();
        date = appointment.getDate();
        time = appointment.getTime();
        agent = appointment.getAgent();
        return this;
    }
}
