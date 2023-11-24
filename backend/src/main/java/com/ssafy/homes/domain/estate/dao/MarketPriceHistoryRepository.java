package com.ssafy.homes.domain.estate.dao;

import com.ssafy.homes.domain.estate.domain.HouseDeal;

import java.util.List;
import java.util.Map;

public interface MarketPriceHistoryRepository {

    // TODO: 실거래가 조회
    // 실거래가를 리스트를 조회 하려면 처음에 위도와 경도 (북동, 남서 => kakaomapAPI 활용)

    // 실거래가 리스트 조회
    List<HouseDeal> selectHouseDeal(Map<String, String>[] latLng);

    // 실거래가 상세 조회
    List<HouseDeal> selectHouseDealDetail(Map<String, Double> latLng);
}
