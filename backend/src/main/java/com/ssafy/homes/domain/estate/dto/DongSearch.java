package com.ssafy.homes.domain.estate.dto;

import com.ssafy.homes.domain.estate.domain.DongCode;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class DongSearch {
    private String dongCode;

    private String sidoName;
    private String gugunName;
    private String dongName;

    @Builder
    private DongSearch(String dongCode, String sidoName, String gugunName, String dongName) {
        this.dongCode = dongCode;
        this.sidoName = sidoName;
        this.gugunName = gugunName;
        this.dongName = dongName;
    }

    public DongCode toEntity(DongSearch dto) {
        return DongCode.builder()
                .dongCode(dto.dongCode)
                .sidoName(dto.sidoName)
                .gugunName(dto.gugunName)
                .dongName(dto.dongName)
                .build();
    }

    public DongSearch toDto(DongCode entity) {
        dongCode = entity.getDongCode();
        sidoName = entity.getSidoName();
        gugunName = entity.getGugunName();
        dongName = entity.getDongName();
        return this;
    }
}
