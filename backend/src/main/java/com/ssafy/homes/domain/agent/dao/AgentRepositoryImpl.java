package com.ssafy.homes.domain.agent.dao;

import static com.ssafy.homes.domain.agent.domain.QAgent.agent;
import static com.ssafy.homes.domain.agent.domain.QAppointment.appointment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.homes.domain.agent.domain.Agent;
import com.ssafy.homes.domain.agent.domain.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AgentRepositoryImpl implements AgentRepositoryQueryDsl{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public Agent selectAgentByEmail(String email) {
        return jpaQueryFactory.selectFrom(agent)
                .where(agent.email.eq(email))
                .fetchOne();
    }
    @Override
    public void saveAppointment(Appointment appointment) {
        em.persist(appointment);
    }

    @Override
    public List<Appointment> selectAgentAppointments(long aid) {
        return jpaQueryFactory.selectFrom(appointment)
                .where(appointment.agent.id.eq(aid))
                .fetch();
    }
}
