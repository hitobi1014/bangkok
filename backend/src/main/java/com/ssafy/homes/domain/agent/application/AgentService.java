package com.ssafy.homes.domain.agent.application;

import com.ssafy.homes.domain.agent.domain.Agent;
import com.ssafy.homes.domain.agent.dto.RequestAppointment;
import com.ssafy.homes.domain.agent.dto.ResponseAgent;

import com.ssafy.homes.domain.agent.dto.ResponseAppointment;
import java.util.List;
import java.util.Optional;

public interface AgentService {

    List<ResponseAgent> getAllAgent();

    ResponseAgent getAgent(long aid);

    List<ResponseAppointment> getAgentAppointments(long aid);

    Agent getAgentByEmail(String email);

    void saveAgent(ResponseAgent agent);

    void saveAppointment(RequestAppointment appointment);
}
