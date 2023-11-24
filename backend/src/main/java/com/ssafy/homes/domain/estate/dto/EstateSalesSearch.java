package com.ssafy.homes.domain.estate.dto;

import com.ssafy.homes.domain.agent.dto.ResponseAgent;
import com.ssafy.homes.domain.estate.domain.EstateSales;
import com.ssafy.homes.domain.estate.domain.EstateSalesType;
import com.ssafy.homes.global.common.file.File;
import com.ssafy.homes.global.common.file.FileDto;
import com.ssafy.homes.domain.estate.domain.EstateTransactionType;
import com.ssafy.homes.domain.estate.domain.HouseInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class EstateSalesSearch {
    private Long id;
    private String title;
    private String content;
    private int salesPrice;
    private int maintenanceCost;
//    private String img;
    private FileDto file;
    private HouseInfoSearch houseInfo;
    private ResponseAgent agent;
    private EstateSalesType estateSalesType;
    private EstateTransactionType transactionType;

    @Builder
    private EstateSalesSearch(String title, String content, int salesPrice, int maintenanceCost, FileDto file, HouseInfoSearch houseInfo
            , ResponseAgent agent, EstateSalesType estateSalesType, EstateTransactionType transactionType) {
        this.title = title;
        this.content = content;
        this.salesPrice = salesPrice;
        this.maintenanceCost = maintenanceCost;
//        this.img = img;
        this.file = file;
        this.houseInfo = houseInfo;
        this.agent = agent;
        this.estateSalesType = estateSalesType;
        this.transactionType = transactionType;
    }

    public EstateSales toEntity(EstateSalesSearch estate) {
        return EstateSales.builder()
                .id(estate.getId())
                .title(estate.getTitle())
                .content(estate.getContent())
                .salesPrice(estate.getSalesPrice())
                .maintenanceCost(estate.getMaintenanceCost())
//                .img(estate.getImg())
                .file(estate.getFile().toEntity())
                .houseInfo(new HouseInfoSearch().toEntity(estate.getHouseInfo()))
                .agent(new ResponseAgent().toEntity(estate.getAgent()))
                .estateSalesType(estate.getEstateSalesType())
                .transactionType(estate.getTransactionType())
                .build();
    }

    public FileDto toFileDto(File file) {
        return FileDto.builder()
                .id(file.getId())
                .originFileName(file.getOriginFileName())
                .fullPath(file.getFullPath())
                .saveFileName(file.getSaveFileName())
                .realPath(file.getRealPath())
                .build();
    }

    public EstateSalesSearch toDto(EstateSales entity) {
        id = entity.getId();
        title = entity.getTitle();
        content = entity.getContent();
        salesPrice = entity.getSalesPrice();
        maintenanceCost = entity.getMaintenanceCost();
//        img = entity.getImg();
        file = toFileDto(entity.getFile());
        houseInfo = new HouseInfoSearch().toDto(entity.getHouseInfo());
        agent = new ResponseAgent().toDto(entity.getAgent());
        estateSalesType = entity.getEstateSalesType();
        transactionType = entity.getTransactionType();
        return this;
    }
}
