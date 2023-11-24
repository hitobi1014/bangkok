package com.ssafy.homes.domain.member.application;

import com.ssafy.homes.domain.member.dao.MemberRepositoryImpl;
import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.domain.member.dto.RequestJoinMember;
import com.ssafy.homes.domain.member.dto.RequestLoginMember;
import com.ssafy.homes.domain.member.dto.ResponseMember;
import com.ssafy.homes.global.common.file.File;
import com.ssafy.homes.global.common.file.FileDto;
import com.ssafy.homes.global.common.file.FileRepository;
import com.ssafy.homes.global.common.file.FileUtils;
import com.ssafy.homes.global.config.AppEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepositoryImpl memberRepository;
    private final AppEnvironment environment;
    private final FileRepository fileRepository;
    private final FileUtils fileUtils;


    /**
     * 회원가입시 id체크를 다른 비즈니스로직으로 할건지 가입할때 할건지 결정해야함
     */
    @Transactional
    @Override
    public void saveMember(RequestJoinMember member, MultipartFile multipartFile) throws Exception {
        // STEP01. 파일 디스크에 업로드
        String realPath = "images/member";
        FileDto fileDto = fileUtils.uploadFile(multipartFile, realPath);
        log.info("파일 저장 후 DTO : {}", fileDto);

        // STEP02. DB에 저장
        member.setMemberFile(fileDto);
        log.info("service 받은 file : {}", member.getMemberFile());
        memberRepository.insertMember(member.toEntity(member));
    }

    /**
     * 회원이 회원을 전체 조회하는 경우? -- x
     * 관리자가 회원을 전체 조회하는 경우
     */
    @Override
    public List<ResponseMember> getAllMember() throws Exception {
//        List<Member> members = memberRepository.selectMemberList();
        return memberRepository.selectMemberList()
                .stream()
                .map(m -> new ResponseMember().toDto(m))
                .collect(Collectors.toList());
    }

    /**
     * 해당 회원 정보가 없을때
     */
    @Override
    public ResponseMember loginMember(RequestLoginMember member) throws Exception {
        Member getMember = memberRepository.selectMemberByEmailIdWithPass(member.toEntity(member));
        File file = memberRepository.selectFile(member.toEntity(member));
        if (file != null) {
            getMember.setFile(file);
        }

        return new ResponseMember().toDto(getMember);
    }

}
