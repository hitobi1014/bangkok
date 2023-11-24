package com.ssafy.homes.domain.estate.api;

import com.ssafy.homes.domain.common.CommonDto;
import com.ssafy.homes.domain.estate.application.MarketPriceHistoryService;
import com.ssafy.homes.domain.estate.dto.RequestMarketPrice;
import com.ssafy.homes.domain.estate.dto.ResponseMarketPriceDetail;
import com.ssafy.homes.domain.estate.dto.ResponseMarketToMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/market")
@Slf4j
public class MarketPriceController {

    private final MarketPriceHistoryService service;

//    # 지도 중심좌표는 위도 37.57465159932322, <br>경도 126.96857500993005
//            # 이고 <br>지도 레벨은 3 입니다 <br> <br>지도 타입은 1 이고 <br>
//# 지도의 남서쪽 좌표는
// 37.57307358026064
// 126.96409295215354
//# <br>북동쪽 좌표는
// 37.57622944787139
// 126.97305725695135

    @PostMapping("/list")
    public ResponseEntity<CommonDto<List<ResponseMarketToMap>>> getMarkets(@RequestBody RequestMarketPrice param) {

        List<ResponseMarketToMap> result = service.getHouseDeal(param);

//        Long result = service.selectHouseDeal(param);
        try {
            return ResponseEntity.ok(CommonDto.of(result.size(), result));

        } catch (Exception e) {
            throw new RuntimeException("해당 marketList를 가져오지 못했습니다", e);
        }
    }

    @GetMapping("/detail/{lat}/{lng}")
    public ResponseEntity<CommonDto<List<ResponseMarketPriceDetail>>> getMarketDetail(@PathVariable Double lat, @PathVariable Double lng) {
        log.info("받은 파라미터 : lat=> {} \t lng=> {}", lat,lng);
        try {
            List<ResponseMarketPriceDetail> result = service.getHouseDealDetail(lat, lng);
            log.info("■■■■■■■■■■■■■■■■■■■■■■■■");
            result.forEach(System.out::println);
            log.info("■■■■■■■■■■■■■■■■■■■■■■■■");

            //TODO: 데이터가 1뎁스까지만 받아와짐
            return ResponseEntity.ok(CommonDto.of(result.size(), result));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
