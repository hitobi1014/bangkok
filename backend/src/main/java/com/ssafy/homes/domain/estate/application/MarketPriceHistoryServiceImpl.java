package com.ssafy.homes.domain.estate.application;

import com.ssafy.homes.domain.estate.dao.MarketPriceHistoryRepository;
import com.ssafy.homes.domain.estate.domain.HouseDeal;
import com.ssafy.homes.domain.estate.domain.HouseInfo;
import com.ssafy.homes.domain.estate.dto.RequestMarketPrice;
import com.ssafy.homes.domain.estate.dto.ResponseMarketPriceDetail;
import com.ssafy.homes.domain.estate.dto.ResponseMarketToMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarketPriceHistoryServiceImpl implements MarketPriceHistoryService {

    private final MarketPriceHistoryRepository repository;

    @Override
    public List<ResponseMarketToMap> getHouseDeal(RequestMarketPrice param) {
        double[] lat = param.getLat();
        double[] lng = param.getLng();

        Map<String, String>[] latLng = new Map[]{
                new HashMap<String, String>(),
                new HashMap<String, String>()
        };

        latLng[0].put("lat", String.valueOf(lat[0]));
        latLng[0].put("lng", String.valueOf(lng[0]));
        latLng[1].put("lat", String.valueOf(lat[1]));
        latLng[1].put("lng", String.valueOf(lng[1]));

        List<ResponseMarketToMap> result = repository.selectHouseDeal(latLng)
                .stream()
                .map(h -> new ResponseMarketToMap().toDto(h))
                .collect(Collectors.toList());

        Function<String, Double> sToD = Double::parseDouble;

        List<ResponseMarketToMap> resultList = result.stream()
                .collect(Collectors.groupingBy(
                        ResponseMarketToMap::getAptCode,
                        Collectors.collectingAndThen(
                                Collectors.maxBy((o1, o2) -> {
                                    if (o1.getDealYear() != o2.getDealYear()) {
                                        return Integer.compare(o1.getDealYear(), o2.getDealYear());
                                    } else {
                                        return Double.compare(sToD.apply(o1.getArea()), sToD.apply(o2.getArea()));
                                    }
                                }),
                                // Optional을 ResponseMarketToMap으로 변환
                                optional -> optional.orElse(null)
                        )
                ))
                .values()
                .stream()
                .filter(responseMarketToMap -> responseMarketToMap != null)
                .collect(Collectors.toList());

        return resultList;
    }

    @Override
    public List<ResponseMarketPriceDetail> getHouseDealDetail(Double getLat, Double getLng) {
        DecimalFormat df = new DecimalFormat("0.0000000000");

        // 소수 10자리까지 반올림, 끝이 0이면 보존
        Double lat = Double.parseDouble(df.format(getLat));
        Double lng = Double.parseDouble(df.format(getLng));

        HashMap<String, Double> param = new HashMap<>();
        param.put("lat", lat);
        param.put("lng",lng);
        log.info("getHouseDealDetail 파라미터 map : lat=> {} \t lng => {}", param.get("lat"), param.get("lng"));

        return repository.selectHouseDealDetail(param)
                .stream()
                .map(list -> new ResponseMarketPriceDetail().toDto(list))
                .collect(Collectors.toList());
    }
}
