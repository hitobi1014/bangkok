package com.ssafy.homes.domain.agent.api;

import com.ssafy.homes.domain.agent.application.AgentService;
import com.ssafy.homes.domain.agent.domain.Agent;
import com.ssafy.homes.domain.agent.dto.ResponseAgent;
import com.ssafy.homes.domain.agent.dto.ResponseAppointment;
import com.ssafy.homes.domain.member.dto.RequestJoinMember;
import com.ssafy.homes.domain.member.dto.RequestLoginMember;
import com.ssafy.homes.domain.member.dto.ResponseMember;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
@ToString
public class AgentController {

    private final AgentService agentService;

    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllAgent() {
        try {
            log.debug("getAllAgent...");
            List<ResponseAgent> getAgents = agentService.getAllAgent();
            return ResponseEntity.ok(getAgents);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping(value = "/detail/{aid}")
    public ResponseEntity<?> getAgent(@PathVariable long aid) {
        try {
            log.debug("getAgent...");
            ResponseAgent getAgent = agentService.getAgent(aid);
            return ResponseEntity.ok(getAgent);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping(value = "/appointments/{aid}")
    public ResponseEntity<?> getAgentAppointments(@PathVariable long aid) {
        try {
            log.debug("getAgentAppointments...");
            List<ResponseAppointment> appointments = agentService.getAgentAppointments(aid);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}