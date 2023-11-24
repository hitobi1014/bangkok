package com.ssafy.homes.domain.estate.api;

import com.ssafy.homes.domain.estate.application.EstateService;
import com.ssafy.homes.domain.estate.domain.DongCode;
import com.ssafy.homes.domain.estate.domain.EstateSales;
import com.ssafy.homes.domain.estate.domain.HouseDeal;
import com.ssafy.homes.domain.estate.domain.HouseInfo;
import com.ssafy.homes.domain.estate.dto.*;
import com.ssafy.homes.domain.member.dto.RequestJoinMember;
import java.util.Optional;

import com.ssafy.homes.domain.member.dto.ResponseMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/estate")
@RequiredArgsConstructor
public class EstateController {

    private final EstateService estateService;

    @GetMapping("/sales")
    public ResponseEntity<?> getSales() {
        try {
            log.debug("getAllMember....");
            List<EstateSalesSearch> getSales = estateService.getAllEstateSales();
            return ResponseEntity.ok(getSales);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/sale/{eid}")
    public ResponseEntity<?> getSale(@PathVariable("eid") long eid) {
        Optional<EstateSales> estateSale = estateService.getEstateSale(eid);
        log.info("getSale : {}", estateSale);

        if (!estateSale.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new EstateSalesSearch().toDto(estateSale.get()));
    }

    @PostMapping("/regist")
    public ResponseEntity<?> saveEstate(@RequestPart(value = "estate") RequestEstate estate, @RequestPart(value = "file", required = false)
        MultipartFile file) throws Exception {
        try {
            log.debug("registEstate....");

            log.info("받은 estate값 : {}", estate);
            log.info("받은 file : {}", file);

            estateService.registerEstateSale(estate, file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping ("/modify/{eid}")
    public ResponseEntity<?> modifyEstate(@PathVariable("eid") long eId, @RequestBody EstateSales estate) throws Exception {
        estateService.modifyEstateSale(eId, estate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{eid}")
    public ResponseEntity<?> deleteEstate(@PathVariable("eid") long eId) throws Exception {
        estateService.deleteEstateSale(eId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deals/{pagenum}")
    public ResponseEntity<?> getDeals(@PathVariable("pagenum")int pageNum, @RequestParam("dong") String dong) {
        System.out.println(pageNum + " " + dong);
        List<HouseDeal> allHouseDeal = estateService.getAllHouseDeal(dong, pageNum);
        List<HouseDealSearch> resultList = allHouseDeal.stream()
                .map(entity -> new HouseDealSearch().toDto(entity))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/houseinfos")
    public ResponseEntity<?> getHouseInfos(@PathVariable("pagenum") int pageNum, @RequestParam("dong") String dong) {
        List<HouseInfo> allHouseInfo = estateService.getAllHouseInfo(dong, pageNum);
        List<HouseInfoSearch> resultList = allHouseInfo.stream()
                .map(entity -> new HouseInfoSearch().toDto(entity))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/houseinfosbyname")
    public ResponseEntity<?> getHouseInfosWithName(@RequestParam("name") String name) {
        List<HouseInfo> allHouseInfo = estateService.getAllHouseInfoByName(name);
        List<HouseInfoSearch> resultList = allHouseInfo.stream()
                .map(entity -> new HouseInfoSearch().toDto(entity))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/dongs/{pagenum}")
    public ResponseEntity<?> getDongs(@PathVariable("pagenum") int pageNum, @RequestParam("sido") String sidoName) {
        List<DongCode> allDong = estateService.getAllDong(sidoName, pageNum);
        List<DongSearch> resultList = allDong.stream()
                .map(entity -> new DongSearch().toDto(entity))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/houseinfos/detail/{aptcode}")
    public ResponseEntity<?> getHouseInfo(@PathVariable("aptcode") long aptCode) {
        HouseInfo houseInfo = estateService.getHouseInfo(aptCode);
        HouseInfoSearch result = new HouseInfoSearch().toDto(houseInfo);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/search-select")
    public ResponseEntity<?> getEstateBySearchSelect(@RequestBody RequestSelectSearchToSales param){
        List<EstateSalesSearch> result = estateService.getEstateBySearchKeyword(param);
        log.info("받은 파라미터 : {}", param);
        return  ResponseEntity.ok(result);
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
