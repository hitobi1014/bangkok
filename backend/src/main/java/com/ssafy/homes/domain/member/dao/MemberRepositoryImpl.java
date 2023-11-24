package com.ssafy.homes.domain.member.dao;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.global.common.file.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssafy.homes.domain.member.domain.QMember.member;
import static com.ssafy.homes.global.common.file.QFile.file;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    // 등록 수정 삭제 조회
    public void insertMember(Member member) {
        em.persist(member);
    }

    public void deleteMember(Long id) {
        Member member = em.find(Member.class, id);
        em.remove(member);
    }

    @Override
    public Member updateMember(Member member) {
        Member getMember = em.find(Member.class, member.getId());
        //TODO: 회원 수정 img 수정하기 => 나중에.. 시간이 된다면
        //        getMember.updateMember(member.getPassword(), member.getName(), member.getMobile(), member.getImg());
        return getMember;
    }

    public Member selectMemberById(Long id) {
        return jpaQueryFactory.selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    public Member selectMemberByEmailIdWithPass(Member param) {
        return jpaQueryFactory.selectFrom(member)
                .where(member.email.eq(param.getEmail())
                        .and(member.emailDomain.eq(param.getEmailDomain()))
                        .and(member.password.eq(param.getPassword()))
                ).fetchOne();
    }

    @Override
    public Member selectMemberByEmailInfo(String email, String emailDomain) {
        return jpaQueryFactory.selectFrom(member)
                .where(member.email.eq(email),
                        member.emailDomain.eq(emailDomain)
                ).fetchOne();
    }

    public List<Member> selectMemberList() {
        return jpaQueryFactory.selectFrom(member)
                .fetch();
    }

    @Override
    public File selectFile(Member param) {
        return jpaQueryFactory.selectFrom(file)
                .where(file.id.eq
                        (
                                JPAExpressions
                                        .select(member.file.id)
                                        .from(member)
                                        .where(member.email.eq(param.getEmail()),
                                                member.emailDomain.eq(param.getEmailDomain()),
                                                member.password.eq(param.getPassword())
                                        )
                        )).fetchOne();
    }

}
