package com.ssafy.homes.domain.estate.dao;

import com.ssafy.homes.domain.estate.domain.EstateSales;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateRepository extends JpaRepository<EstateSales, Long>, EstateRepositoryQueryDsl {
}
