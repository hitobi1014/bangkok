package com.ssafy.homes.domain.estate.dto;

import com.ssafy.homes.domain.agent.dto.ResponseAgent;
import com.ssafy.homes.domain.estate.domain.EstateSales;
import com.ssafy.homes.domain.estate.domain.EstateSalesType;
import com.ssafy.homes.global.common.file.File;
import com.ssafy.homes.global.common.file.FileDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestEstate {
    private String title;
    private String content;
    private int salesPrice;
    private int maintenanceCost;
//    private String img;
    private FileDto file;
    private HouseInfoSearch houseInfo;
    private ResponseAgent agent;
    private EstateSalesType estateSalesType;

    @Builder
    private RequestEstate(String title, String content, int salesPrice, int maintenanceCost, FileDto file, HouseInfoSearch houseInfo, ResponseAgent agent, EstateSalesType estateSalesType) {
        this.title = title;
        this.content = content;
        this.salesPrice = salesPrice;
        this.maintenanceCost = maintenanceCost;
//        this.img = img;
        this.file = file;
        this.houseInfo = houseInfo;
        this.agent = agent;
        this.estateSalesType = estateSalesType;
    }

    public EstateSales toEntity(RequestEstate estate) {
        File file = estate.getFile().toEntity();
        return EstateSales.builder()
            .title(estate.getTitle())
            .content(estate.getContent())
            .salesPrice(estate.getSalesPrice())
            .maintenanceCost(estate.getMaintenanceCost())
//            .img(estate.getImg())
            .file(file)
            .houseInfo(new HouseInfoSearch().toEntity(estate.getHouseInfo()))
            .agent(new ResponseAgent().toEntity(estate.getAgent()))
            .estateSalesType(estate.getEstateSalesType())
            .build();
    }
}