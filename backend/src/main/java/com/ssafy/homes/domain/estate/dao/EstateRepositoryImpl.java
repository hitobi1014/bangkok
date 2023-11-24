package com.ssafy.homes.domain.estate.dao;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.homes.domain.estate.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static com.ssafy.homes.domain.agent.domain.QAgent.agent;
import static com.ssafy.homes.domain.estate.domain.QDongCode.dongCode1;
import static com.ssafy.homes.domain.estate.domain.QEstateSales.estateSales;
import static com.ssafy.homes.domain.estate.domain.QHouseDeal.houseDeal;
import static com.ssafy.homes.domain.estate.domain.QHouseInfo.houseInfo;

@Repository
@RequiredArgsConstructor
public class EstateRepositoryImpl implements EstateRepositoryQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;
    private static final int PAGE_SIZE = 100; // 임시사용 : 페이지 건수

    @Override
    public List<EstateSales> selectEstateSales() {
        return jpaQueryFactory.selectFrom(estateSales)
                .fetch();
    }

    @Override
    public long updateEstateSales(Long eId, EstateSales estate) {
        return jpaQueryFactory
                .update(estateSales)
                .set(estateSales.title, estate.getTitle())
                .set(estateSales.content, estate.getContent())
                .set(estateSales.salesPrice, estate.getSalesPrice())
                .set(estateSales.maintenanceCost, estate.getMaintenanceCost())
//                .set(estateSales.img, estate.getImg())
                .set(estateSales.file, estate.getFile())
                .where(estateSales.id.eq(eId))
                .execute();
    }

    @Override
    public List<EstateSales> selectEstateSalesByKeyword(EstateSales estate) {
        // keyword 정해지면 구현
        return null;
    }

    @Override
    public List<HouseDeal> selectHouseDealList(String dong, int pageNum) {
        return jpaQueryFactory.selectFrom(houseDeal)
                .leftJoin(houseDeal.houseInfo, houseInfo)
                .on(houseInfo.dong.contains(dong))
                .leftJoin(houseInfo.dongCode, dongCode1)
                .offset(findOffset(pageNum))
                .limit(PAGE_SIZE)
                .fetch();
    }

    @Override
    public List<HouseInfo> selectHouseInfoList(String dong, int pageNum) {
        return jpaQueryFactory.selectFrom(houseInfo)
                .join(houseInfo.dongCode, dongCode1)
                .where(houseInfo.dong.contains(dong))
                .offset(findOffset(pageNum))
                .limit(PAGE_SIZE)
                .fetch();
    }

    @Override
    public List<HouseInfo> selectHouseInfoListByName(String name) {
        return jpaQueryFactory.selectFrom(houseInfo)
                .where(houseInfo.dong.contains(name)
                        .or(houseInfo.apartmentName.contains(name)))
                .limit(PAGE_SIZE)
                .fetch();
    }


    @Override
    public List<DongCode> selectDongList(String sidoName, int pageNum) {
        return jpaQueryFactory.selectFrom(dongCode1)
                .where(dongCode1.sidoName.contains(sidoName))
                .offset(findOffset(pageNum))
                .limit(PAGE_SIZE)
                .fetch();
    }

    @Override
    public HouseInfo selectHouseInfoDetail(long aptCode) {
        return jpaQueryFactory.selectFrom(houseInfo)
                .leftJoin(houseInfo.dongCode, dongCode1)
                .where(houseInfo.aptCode.eq(aptCode))
                .fetchOne();

    }

    @Override
    public HouseDeal selectHouseDealDetail(long aptCode) {
        return jpaQueryFactory.selectFrom(houseDeal)
                .where(houseInfo.aptCode.eq(aptCode))
                .fetchOne();
    }

    @Override
    public List<EstateSales> selectEstateSalesBySearchKeyword(Map<String, String> param) {
        String swlat = param.get("swlat"); // 필수값
        String swlng = param.get("swlng"); // 필수값
        String nelat = param.get("nelat"); // 필수값
        String nelng = param.get("nelng"); // 필수값
        String estateSalesType = param.get("estateSalesType");
        String transactionType = param.get("transactionType");
        String salesPrice = param.get("salesPrice");
//        String area = param.get("area");
        String year = param.get("year");

//        select *
//                from estatesales
//        where aptCode in (select houseinfo.aptCode
//                from houseinfo
//                where lat between 126.968575235313 and 126.968575235313
//        and lng between 37.5746540320628 and 37.5746540320628
//        and buildYear between year and year
//                  )
//        and estateSalesType = 매물종류
//        and transactionType = 거래종류
//        and salesPrice between 0 and salesPrice
        return jpaQueryFactory.selectFrom(estateSales)
                .where(estateSales.houseInfo.aptCode.in(
                                JPAExpressions.select(houseInfo.aptCode)
                                        .from(houseInfo)
                                        .where(houseInfo.lat.between(swlat, nelat),
                                                houseInfo.lng.between(swlng, nelng),
                                                eqBuildYear(year)
                                        )
                        ),
                        eqEstateSalesType(estateSalesType),
                        eqTransactionType(transactionType),
                        betweenSalesPrice(salesPrice)
                ).fetch();
    }

    private BooleanExpression eqBuildYear(String year) {
        if (!StringUtils.hasText(year)){
            return null;
        };
        return houseInfo.buildYear.eq(Integer.parseInt(year));
    }

    private BooleanExpression betweenSalesPrice(String salesPrice) {
        if (!StringUtils.hasText(salesPrice)) {
            return null;
        }
        int price = Integer.parseInt(salesPrice);
        int a = 0,b =0;
        if (price == 0.1) {
            b = 10_000_000;
        } else if (price == 2) {
            a = 10_000_000;
            b = 20_000_000;
        } else if (price == 3) {
            a = 20_000_000;
            b = 30_000_000;
        } else if (price ==4){
            a = 30_000_000;
            b = 40_000_000;
        }
        return estateSales.salesPrice.between(a, b);
    }

    private BooleanExpression eqTransactionType(String transactionType){
        if (!StringUtils.hasText(transactionType)){
            return null;
        }

        if ("ALL".equals(transactionType)) {
            return null;
        }

        EstateTransactionType type = EstateTransactionType.valueOf(transactionType);
        return estateSales.transactionType.eq(type);
    }

    private BooleanExpression eqEstateSalesType(String estateSalesType){
        if (!StringUtils.hasText(estateSalesType)){
            return null;
        }

        if ("ALL".equals(estateSalesType)) {
            return null;
        }

        EstateSalesType type = EstateSalesType.valueOf(estateSalesType);
        return estateSales.estateSalesType.eq(type);
    }

    public int findOffset(int pageNum) {
        return (pageNum * PAGE_SIZE) - PAGE_SIZE;
    }
}