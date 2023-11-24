package com.ssafy.homes.domain.agent.dao;

import com.ssafy.homes.domain.agent.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>, AgentRepositoryQueryDsl {
}
