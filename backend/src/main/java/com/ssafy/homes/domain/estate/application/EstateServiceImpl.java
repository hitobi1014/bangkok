package com.ssafy.homes.domain.estate.application;

import com.ssafy.homes.domain.estate.dao.EstateRepository;
import com.ssafy.homes.domain.estate.domain.DongCode;
import com.ssafy.homes.domain.estate.domain.EstateSales;
import com.ssafy.homes.domain.estate.domain.HouseDeal;
import com.ssafy.homes.domain.estate.domain.HouseInfo;
import com.ssafy.homes.domain.estate.dto.EstateSalesSearch;
import com.ssafy.homes.domain.estate.dto.RequestEstate;
import com.ssafy.homes.global.common.file.FileDto;
import com.ssafy.homes.global.common.file.FileUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ssafy.homes.domain.estate.dto.RequestSelectSearchToSales;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EstateServiceImpl implements EstateService {
    private final EstateRepository estateRepository;
    private final FileUtils fileUtils;

    @Override
    public List<EstateSalesSearch> getAllEstateSales() {
        return estateRepository.selectEstateSales().stream()
                .map(e -> new EstateSalesSearch().toDto(e))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Optional<EstateSales> getEstateSale(long eid) {
        return estateRepository.findById(eid);
    }

    @Override
    public void registerEstateSale(RequestEstate estate, MultipartFile file) throws Exception {
        String realPath = "images/estate";
        FileDto fileDto = fileUtils.uploadFile(file, realPath);
        log.info("파일 저장 후 DTO : {}", fileDto);

        estate.setFile(fileDto);
        log.info("service 받은 file : {}", estate.getFile());
        estateRepository.save(estate.toEntity(estate));
    }

    @Override
    public void modifyEstateSale(long eId, EstateSales estate) throws Exception {
        estateRepository.updateEstateSales(eId, estate);
    }

    @Override
    public void deleteEstateSale(long eid) throws Exception {
        estateRepository.deleteById(eid);
    }

    @Override
    public List<HouseDeal> getAllHouseDeal(String dong, int pageNum) {
        return estateRepository.selectHouseDealList(dong, pageNum);
    }

    @Override
    public List<HouseInfo> getAllHouseInfo(String dong, int pageNum) {
        return estateRepository.selectHouseInfoList(dong, pageNum);
    }

    @Override
    public List<HouseInfo> getAllHouseInfoByName(String name) {
        return estateRepository.selectHouseInfoListByName(name);
    }

    @Override
    public List<DongCode> getAllDong(String sidoName, int pageNum) {
        return estateRepository.selectDongList(sidoName, pageNum);
    }

    @Override
    public HouseInfo getHouseInfo(long aptCode) {
        return estateRepository.selectHouseInfoDetail(aptCode);
    }

    @Override
    public HouseDeal getHouseDeal(long aptCode) {
        return estateRepository.selectHouseDealDetail(aptCode);
    }

    @Override
    public List<EstateSalesSearch> getEstateBySearchKeyword(RequestSelectSearchToSales param) {
        Map<String, String> map = new HashMap<>();

        String estateSalesType = param.getEstateSalesType();
        String transactionType = param.getTransactionType();
        Long salesPrice = param.getSalesPrice();
        Double area = param.getArea();
        String year = param.getYear();
        Double[] swLatLng;
        Double[] neLatLng;

        if (param.getSwLatLng() != null) {
            swLatLng = param.getSwLatLng();
            map.put("swlat", String.valueOf(swLatLng[0]));
            map.put("swlng", String.valueOf(swLatLng[1]));
        }
        if (param.getNeLatLng() != null) {
            neLatLng = param.getNeLatLng();
            map.put("nelat", String.valueOf(neLatLng[0]));
            map.put("nelng", String.valueOf(neLatLng[1]));
        }

        map.put("estateSalesType", estateSalesType);
        map.put("transactionType", transactionType);

        map.put("salesPrice", salesPrice == null ? null : String.valueOf(salesPrice));
        map.put("area", area == null ? null : String.valueOf(area));
        map.put("year", year);

//        List<EstateSales> estateSales = estateRepository.selectEstateSalesBySearchKeyword(map);

        return estateRepository.selectEstateSalesBySearchKeyword(map)
                .stream()
                .map(list -> new EstateSalesSearch().toDto(list))
                .collect(Collectors.toList());
    }
}
