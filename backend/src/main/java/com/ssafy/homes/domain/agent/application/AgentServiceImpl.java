package com.ssafy.homes.domain.agent.application;

import com.ssafy.homes.domain.agent.dao.AgentRepository;
import com.ssafy.homes.domain.agent.domain.Agent;
import com.ssafy.homes.domain.agent.dto.RequestAppointment;
import com.ssafy.homes.domain.agent.dto.ResponseAgent;
import com.ssafy.homes.domain.agent.dto.ResponseAppointment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    @Override
    public List<ResponseAgent> getAllAgent() {
        return agentRepository.findAll().stream()
                .map(entity -> new ResponseAgent().toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseAgent getAgent(long aid) {
        Optional<Agent> getAgent = agentRepository.findById(aid);
        if (!getAgent.isPresent()) {
            return null;
        } else {
            return new ResponseAgent().toDto(getAgent.get());
        }
    }

    @Override
    public List<ResponseAppointment> getAgentAppointments(long aid) {
        return agentRepository.selectAgentAppointments(aid).stream()
                .map(entity -> new ResponseAppointment().toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public Agent getAgentByEmail(String email) {
        return agentRepository.selectAgentByEmail(email);
    }

    @Override
    public void saveAgent(ResponseAgent agent){
        if (agent.getId() != null) {
            Optional<Agent> getAgent = agentRepository.findById(agent.getId());
            if (!getAgent.isPresent()) {
                agentRepository.save(agent.toEntity(agent));
            }
        } else {
            agentRepository.save(agent.toEntity(agent));
        }
    }

    @Override
    public void saveAppointment(RequestAppointment appointment) {
        agentRepository.saveAppointment(appointment.toEntity(appointment));
    }
}
