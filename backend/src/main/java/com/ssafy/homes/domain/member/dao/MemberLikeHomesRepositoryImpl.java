package com.ssafy.homes.domain.member.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.domain.member.domain.MemberLikeHomes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


import static com.ssafy.homes.domain.member.domain.QMemberLikeHomes.memberLikeHomes;
@Repository
@RequiredArgsConstructor
public class MemberLikeHomesRepositoryImpl implements MemberLikeHomesRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public List<MemberLikeHomes> selectLikeHomes(Member member) {
        return jpaQueryFactory.selectFrom(memberLikeHomes)
                .where(memberLikeHomes.member.id.eq(member.getId()))
                .fetch();
    }

    @Override
    public void insertLikeHomes(MemberLikeHomes memberLikeHomes) {
        em.persist(memberLikeHomes);
    }

    @Override
    public void deleteLikeHomes(Long id) {
        MemberLikeHomes likeHomes = em.find(MemberLikeHomes.class, id);
        em.remove(likeHomes);
    }
}

