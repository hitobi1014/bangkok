package com.ssafy.homes.domain.member.application;

import com.ssafy.homes.domain.estate.dao.EstateRepository;
import com.ssafy.homes.domain.estate.domain.EstateSales;
import com.ssafy.homes.domain.member.dao.MemberLikeHomesRepository;
import com.ssafy.homes.domain.member.dao.MemberRepositoryImpl;
import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.domain.member.domain.MemberLikeHomes;
import com.ssafy.homes.domain.member.dto.RequestLoginMember;
import com.ssafy.homes.domain.member.dto.RequsetRegistLikeHome;
import com.ssafy.homes.domain.member.dto.ResponseLikeHomes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberLikeHomesServiceImpl implements MemberLikeHomesService {

    private final MemberLikeHomesRepository likeHomesRepository;
    private final MemberRepositoryImpl memberRepository;
    private final EstateRepository estateRepository;

    @Override
    public List<ResponseLikeHomes> getLikeHomes(RequestLoginMember member) {
        Member param = member.toEntity(member);
        // 로그인된 정보로 회원 정보 가져오기
        Member getMember = memberRepository.selectMemberByEmailIdWithPass(param);

        return likeHomesRepository.selectLikeHomes(getMember)
                .stream()
                .map(l -> new ResponseLikeHomes().toDto(l))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registLikeHomes(RequsetRegistLikeHome likeHome) {
        // 회원, 매물 조회
        Member getMember = memberRepository.selectMemberByEmailInfo(likeHome.getEmail(), likeHome.getEmailDomain());
        Optional<EstateSales> getEstate = estateRepository.findById(likeHome.getEstateId());
        MemberLikeHomes param = new RequsetRegistLikeHome().toEntity(getMember, getEstate.get());

        likeHomesRepository.insertLikeHomes(param);
    }

    @Override
    @Transactional
    public void removeLikeHomes(Long id) {
        likeHomesRepository.deleteLikeHomes(id);
    }
}
