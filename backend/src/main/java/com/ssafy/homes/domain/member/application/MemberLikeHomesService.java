package com.ssafy.homes.domain.member.application;

import com.ssafy.homes.domain.member.domain.MemberLikeHomes;
import com.ssafy.homes.domain.member.dto.RequestLoginMember;
import com.ssafy.homes.domain.member.dto.RequsetRegistLikeHome;
import com.ssafy.homes.domain.member.dto.ResponseLikeHomes;

import java.util.List;

public interface MemberLikeHomesService {

    List<ResponseLikeHomes> getLikeHomes(RequestLoginMember member);
    void registLikeHomes(RequsetRegistLikeHome likeHome);

    void removeLikeHomes(Long id);
}
