package com.ssafy.homes.domain.estate.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.homes.domain.agent.domain.Agent;
import com.ssafy.homes.domain.estate.dto.HouseInfoSearch;
import com.ssafy.homes.global.common.file.File;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "estatesales")
public class EstateSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    private int salesPrice;
    private int maintenanceCost;
//    private String img;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fileId")
    private File file;

    @Enumerated(EnumType.STRING)
    private EstateTransactionType transactionType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aptCode")
    private HouseInfo houseInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agentId")
    private Agent agent;

    @Enumerated(EnumType.STRING)
    private EstateSalesType estateSalesType;

    @Builder
    private EstateSales(Long id, String title, String content, int salesPrice, int maintenanceCost, String img, File file
            , HouseInfo houseInfo, Agent agent, EstateSalesType estateSalesType, EstateTransactionType transactionType) {
        this.id = id;
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

    public EstateSales updateEstateSales(String title, String content, int salesPrice, int maintenanceCost, File file) {
        this.title = title;
        this.content = content;
        this.salesPrice = salesPrice;
        this.maintenanceCost = maintenanceCost;
//        this.img = img;
        return this;
    }
}
