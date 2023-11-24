package com.ssafy.homes.domain.estate.dao;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.homes.domain.estate.domain.HouseDeal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static com.ssafy.homes.domain.estate.domain.QHouseDeal.houseDeal;
import static com.ssafy.homes.domain.estate.domain.QHouseInfo.houseInfo;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MarketPriceHistoryRepositoryImpl implements MarketPriceHistoryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public List<HouseDeal> selectHouseDeal(Map<String, String>[] latLng) {
        String lat_0 = latLng[0].get("lat");
        String lng_0 = latLng[0].get("lng");
        String lat_1 = latLng[1].get("lat");
        String lng_1 = latLng[1].get("lng");

        return jpaQueryFactory.selectFrom(houseDeal)
                .where(houseDeal.houseInfo.aptCode.in(
                        JPAExpressions.select(houseInfo.aptCode)
                                .from(houseInfo)
                                .where(houseInfo.lat.between(lat_0, lat_1),
                                        houseInfo.lng.between(lng_0, lng_1)
                                )
                )).fetch();
    }

    @Override
    public List<HouseDeal> selectHouseDealDetail(Map<String, Double> latLng) {
//        select *
//                from housedeal
//        where aptCode = (select houseinfo.aptCode
//        from houseinfo
//        where round(lat, 10) = 37.4969514046
//        and round(lng, 10) = 127.0372270238);

//        List<HouseDeal> result = jpaQueryFactory.selectFrom(houseDeal)
//                .where(houseDeal.houseInfo.aptCode.eq(
//                        JPAExpressions
//                                .select(houseInfo.aptCode)
//                                .from(houseInfo)
//                                .where(
//                                        Expressions.numberTemplate(Double.class, "round(cast{0} as double, 10)", houseInfo.lat)
//                                                .eq(latLng.get("lat")).and(
//                                                        Expressions.numberTemplate(Double.class, "round(cast{0} as double, 10)", houseInfo.lng)
//                                                                .eq(latLng.get("lng"))
//                                                )
//                                )
//                )).fetch();
//                                        MathExpressions.round(houseInfo.lat, 10).eq(latLng.get("lat")),
//                                        MathExpressions.round(houseInfo.lng, 10).eq(latLng.get("lng"))

//        List<HouseDeal> result = jpaQueryFactory.selectFrom(houseDeal)
//                .where(houseDeal.houseInfo.aptCode
//                        .eq(JPAExpressions.select(houseInfo.aptCode)
//                                .from(houseInfo)
//                                .where(
//                                        Expressions.numberTemplate(Double.class, "round(cast({0} as double), 10)", houseInfo.lat)
//                                                .eq(latLng.get("lat"))
//                                                .and(
//                                                        Expressions.numberTemplate(Double.class, "round(cast({0} as double), 10)", houseInfo.lng)
//                                                                .eq(latLng.get("lng"))
//                                                )
//                                )
//                        )
//                ).fetch();

        List<HouseDeal> result = jpaQueryFactory.selectFrom(houseDeal)
                .join(houseDeal.houseInfo, houseInfo).fetchJoin()
                .where(houseInfo.aptCode.eq(
                                JPAExpressions.select(houseInfo.aptCode)
                                        .from(houseInfo)
                                        .where(
                                                Expressions.numberTemplate(Double.class, "round(cast({0} as double), 10)", houseInfo.lat)
                                                        .eq(latLng.get("lat"))
                                                        .and(
                                                                Expressions.numberTemplate(Double.class, "round(cast({0} as double), 10)", houseInfo.lng)
                                                                        .eq(latLng.get("lng"))
                                                        )
                                        )
                        )
                )
                .fetch();


        return result;
    }
}
