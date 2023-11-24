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
public class ResponseAppointment {

    private Long no;
    private String date;
    private String time;

    @Builder
    public ResponseAppointment(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public Appointment toEntity(ResponseAppointment appointment) {
        return Appointment.builder()
                .no(appointment.getNo())
                .date(appointment.getDate())
                .time(appointment.getTime())
                .build();
    }

    public ResponseAppointment toDto(Appointment appointment) {
        no = appointment.getNo();
        date = appointment.getDate();
        time = appointment.getTime();
        return this;
    }
}
