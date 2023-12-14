package com.team2.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.member.model.Member;
import com.team2.member.repository.IMemberRepository;

@Service
public class MemberService implements IMemberService {

    @Autowired
    IMemberRepository memberDao;

    @Override
    public Member selectMember(String memberId) {
        return memberDao.selectMember(memberId);
    }

    @Override
    public void insertMember(Member member) {
        memberDao.insertMember(member);
    }

    @Override
    public void updateMember(Member member) {
        memberDao.updateMember(member);
    }

    @Override
    public void deleteMember(Member member) {
        memberDao.deleteMember(member);
    }

    @Override
    public String getId(String email, String phone) {
        return memberDao.getId(email, phone);
    }

    @Override
    public String updatePassword(String memberId, String password, String email, String phone) {
        memberDao.updatePassword(memberId, password, email, phone);
        memberDao.getPassword(memberId, email, phone);
        return "비밀번호 업데이트 성공";
    }

	@Override
	public boolean checkEmail(String email) {
		return memberDao.checkEmail(email);
	}

	@Override
	public boolean checkMemberId(String memberId) {
		return memberDao.checkMemberId(memberId);
	}
}

