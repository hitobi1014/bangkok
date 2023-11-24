package com.ssafy.homes.domain.member.dto;

import com.ssafy.homes.domain.estate.domain.EstateSalesType;
import com.ssafy.homes.domain.estate.domain.EstateTransactionType;
import com.ssafy.homes.domain.estate.domain.HouseInfo;
import com.ssafy.homes.domain.estate.dto.HouseInfoSearch;
import com.ssafy.homes.domain.member.domain.MemberLikeHomes;
import com.ssafy.homes.global.common.file.File;
import com.ssafy.homes.global.common.file.FileDto;
import lombok.Data;

@Data
public class ResponseLikeHomes {

    private Long id;
    private Long esateSalesId;
    private String title;
    private int salesPrice;
    private FileDto fileDto;
    private EstateTransactionType transactionType;
    private EstateSalesType estateSalesType;
    private HouseInfoSearch houseInfo;

    public ResponseLikeHomes toDto(MemberLikeHomes homes) {
        this.id = homes.getId();
        this.esateSalesId = homes.getEstateSales().getId();
        this.title = homes.getEstateSales().getTitle();
        this.salesPrice = homes.getEstateSales().getSalesPrice();
//        this.img = homes.getEstateSales().getImg();
        File file = homes.getEstateSales().getFile();
        if (file != null) {
            this.fileDto = new ResponseMember().toFileDto(file);
        }
        this.transactionType = homes.getEstateSales().getTransactionType();
        this.estateSalesType = homes.getEstateSales().getEstateSalesType();
        HouseInfo info = homes.getEstateSales().getHouseInfo();
        if (info != null) {
            this.houseInfo = new HouseInfoSearch().toDto(info);
        }

        return this;
    }
}
