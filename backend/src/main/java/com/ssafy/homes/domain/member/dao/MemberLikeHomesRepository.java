package com.ssafy.homes.domain.member.dao;

import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.domain.member.domain.MemberLikeHomes;

import java.util.List;

public interface MemberLikeHomesRepository {

    // 관심매물 리스트 조회, 등록, 삭제

    List<MemberLikeHomes> selectLikeHomes(Member member);
    void insertLikeHomes(MemberLikeHomes memberLikeHomes);

    void deleteLikeHomes(Long id);
}
