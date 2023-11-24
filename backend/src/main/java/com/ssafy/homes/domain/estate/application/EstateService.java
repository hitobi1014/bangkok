package com.ssafy.homes.domain.estate.application;

import com.ssafy.homes.domain.estate.domain.DongCode;
import com.ssafy.homes.domain.estate.domain.EstateSales;
import com.ssafy.homes.domain.estate.domain.HouseDeal;
import com.ssafy.homes.domain.estate.domain.HouseInfo;

import com.ssafy.homes.domain.estate.dto.EstateSalesSearch;
import com.ssafy.homes.domain.estate.dto.RequestSelectSearchToSales;

import java.util.List;
import java.util.Optional;

import com.ssafy.homes.domain.estate.dto.RequestEstate;
import org.springframework.web.multipart.MultipartFile;

public interface EstateService {
    List<EstateSalesSearch> getAllEstateSales();

    Optional<EstateSales> getEstateSale(long eid);

    void registerEstateSale(RequestEstate estate, MultipartFile file) throws Exception;

    void modifyEstateSale(long eId, EstateSales estate) throws Exception;

    void deleteEstateSale(long eid) throws Exception;
    List<HouseDeal> getAllHouseDeal(String dong, int pageNum);

    List<HouseInfo> getAllHouseInfo(String dong, int pageNum);

    List<HouseInfo> getAllHouseInfoByName(String name);

    List<DongCode> getAllDong(String sidoName, int pageNum);

    HouseInfo getHouseInfo(long aptCode);

    HouseDeal getHouseDeal(long aptCode);

    List<EstateSalesSearch> getEstateBySearchKeyword(RequestSelectSearchToSales param);
}
