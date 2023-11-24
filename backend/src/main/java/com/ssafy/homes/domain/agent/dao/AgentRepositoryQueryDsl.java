package com.ssafy.homes.domain.agent.dao;

import com.ssafy.homes.domain.agent.domain.Agent;
import com.ssafy.homes.domain.agent.domain.Appointment;

import java.util.List;
import java.util.Optional;

public interface AgentRepositoryQueryDsl {

    Agent selectAgentByEmail(String email);

    void saveAppointment(Appointment appointment);

    List<Appointment> selectAgentAppointments(long aid);
}
