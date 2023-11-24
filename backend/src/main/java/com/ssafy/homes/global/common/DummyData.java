package com.ssafy.homes.global.common;

import com.ssafy.homes.domain.agent.application.AgentService;
import com.ssafy.homes.domain.agent.domain.Agent;
import com.ssafy.homes.domain.agent.domain.Appointment;
import com.ssafy.homes.domain.agent.dto.ResponseAgent;
import com.ssafy.homes.domain.agent.dto.RequestAppointment;
import com.ssafy.homes.domain.estate.application.EstateService;
import com.ssafy.homes.domain.estate.domain.EstateSales;
import com.ssafy.homes.domain.estate.domain.EstateSalesType;
import com.ssafy.homes.domain.estate.dto.EstateSalesSearch;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DummyData {
    @Autowired
    private EstateService estateService;
    @Autowired
    private AgentService agentService;

    @Transactional
    @EventListener(ApplicationReadyEvent.class) // Application이 실행되고 나서 이 메소드를 실행한다.
    public void setUp() throws Exception {

//        Agent agent1 = Agent.builder()
//                .name("김철수")
//                .address("서울시 강남구")
//                .email("test1")
//                .emailDomain("naver.com")
//                .password("1234")
//                .phone("01012345678")
//                .img("img1")
//                .build();
//
//        ResponseAgent ResponseAgent1 = new ResponseAgent().toDto(agent1);
//        agentService.saveAgent(ResponseAgent1);
//
//        Agent savedAgent = new ResponseAgent().toEntity(agentService.getAgent(1L));
//
//        agentService.saveAppointment(new RequestAppointment().toDto(Appointment.builder()
//                .agent(agent1)
//                .date("2023-11-26")
//                .time("10:00")
//                .agent(savedAgent)
//                .build()));
//        agentService.saveAppointment(new RequestAppointment().toDto(Appointment.builder()
//                .agent(agent1)
//                .date("2023-11-28")
//                .time("09:00")
//                .agent(savedAgent)
//                .build()));
//        agentService.saveAppointment(new RequestAppointment().toDto(Appointment.builder()
//                .agent(agent1)
//                .date("2023-11-27")
//                .time("12:00")
//                .agent(savedAgent)
//                .build()));
//
//        EstateSales estateSales1 = EstateSales.builder()
//                                                .title("아파트1")
//                                                .content("이거 정말 좋아")
//                                                .salesPrice(100000000)
//                                                .maintenanceCost(10000)
//                                                .img("대충 좋은 아파트")
//                                                .houseInfo(estateService.getHouseInfo(11110000000001L))
//                                                .agent(agentService.getAgentByEmail("test1"))
//                                                .estateSalesType(EstateSalesType.APT)
//                                                .build();
//        EstateSalesSearch estateSalesSearch1 = new EstateSalesSearch().toDto(estateSales1);
//        EstateSales estateSales2 = EstateSales.builder()
//                                                .title("아파트2")
//                                                .content("이거 정말 별로야")
//                                                .salesPrice(500000000)
//                                                .maintenanceCost(5000)
//                                                .img("대충 나쁜 아파트")
//                                                .houseInfo(estateService.getHouseInfo(11110000000002L))
//                                                .agent(agentService.getAgentByEmail("test1"))
//                                                .estateSalesType(EstateSalesType.VILLA)
//                                                .build();
//        EstateSalesSearch estateSalesSearch2 = new EstateSalesSearch().toDto(estateSales2);
//        estateService.registerEstateSale(estateSalesSearch1);
//        estateService.registerEstateSale(estateSalesSearch2);
    }
}