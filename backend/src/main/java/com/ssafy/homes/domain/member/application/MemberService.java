package com.ssafy.homes.domain.member.application;

import com.ssafy.homes.domain.member.dto.RequestJoinMember;
import com.ssafy.homes.domain.member.dto.RequestLoginMember;
import com.ssafy.homes.domain.member.dto.ResponseMember;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberService {
    void saveMember(RequestJoinMember member, MultipartFile multipartFile) throws Exception;

    List<ResponseMember> getAllMember() throws Exception;

    ResponseMember loginMember(RequestLoginMember member) throws Exception;

}
