package com.team2.member.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.member.model.Member;
import com.team2.member.model.MemberDelete;
import com.team2.member.model.MemberFindInfo;
import com.team2.member.model.Email;
import com.team2.member.model.MemberId;
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
    public int deleteMember(MemberDelete memberDelete) {
        return memberDao.deleteMember(memberDelete);
    }

    @Override
    public String getId(MemberFindInfo memberFindInfo) {
        return memberDao.getId(memberFindInfo.getEmail(), memberFindInfo.getPhone());
    }

    @Override
    @Transactional("transactionManager")
    public String updatePassword(MemberFindInfo memberFindInfo) {
    	//비밀번호 랜덤값으로 생성
    	Random random = new Random();
        StringBuffer password = new StringBuffer();
        while(password.length()<8){
            if(random.nextBoolean()){
                password.append((char)((int)(random.nextInt(26))+65));
            }
            else{
                password.append(random.nextInt(10));
            }                
        }
        
        //회원정보 업데이트
        int result = memberDao.updatePassword(memberFindInfo.getMemberId(), password.toString(), memberFindInfo.getEmail(), memberFindInfo.getPhone());
        if(result != 1) {
        	return "회원정보가 일치하지 않습니다.";
        }
        
        //업데이트된 비밀번호 검색
        memberDao.getPassword(memberFindInfo);
        return password.toString();
    }

	@Override
	public boolean checkEmail(Email email) {
		return memberDao.checkEmail(email);		
	}

	@Override
	public boolean checkMemberId(MemberId memberId) {
		return memberDao.checkMemberId(memberId);
	}
}

