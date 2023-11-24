package com.ssafy.homes.domain.estate.dao;

import com.ssafy.homes.domain.estate.domain.DongCode;
import com.ssafy.homes.domain.estate.domain.EstateSales;
import com.ssafy.homes.domain.estate.domain.HouseDeal;
import com.ssafy.homes.domain.estate.domain.HouseInfo;
import java.util.List;
import java.util.Map;

public interface EstateRepositoryQueryDsl {

    /**
     * 모든 아파트거래, 아파트정보, 동 정보는
     * 전체조회시 너무 많으므로 필수로 파라미터 받음
     */
    List<EstateSales> selectEstateSales();

    long updateEstateSales(Long eId, EstateSales estate);

    List<EstateSales> selectEstateSalesByKeyword(EstateSales estate);

    List<DongCode> selectDongList(String sidoName, int pageNum);

    List<HouseDeal> selectHouseDealList(String dong, int pageNum);

    List<HouseInfo> selectHouseInfoList(String dong, int pageNum);

    List<HouseInfo> selectHouseInfoListByName(String name);

    HouseInfo selectHouseInfoDetail(long aptCode);

    HouseDeal selectHouseDealDetail(long aptCode);

    List<EstateSales> selectEstateSalesBySearchKeyword(Map<String, String> param);
}
