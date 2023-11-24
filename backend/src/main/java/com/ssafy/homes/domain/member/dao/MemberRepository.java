package com.ssafy.homes.domain.member.dao;

import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.global.common.file.File;

import java.util.List;

public interface MemberRepository {
    void insertMember(Member member);

    void deleteMember(Long id);

    Member updateMember(Member member);

    Member selectMemberById(Long id);

    Member selectMemberByEmailIdWithPass(Member member);

    Member selectMemberByEmailInfo(String email, String emailDomain);

    List<Member> selectMemberList();

    File selectFile(Member param);

}
