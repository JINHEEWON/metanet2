package com.team2.member.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public String getPassword(String memberId) {
        return memberDao.getPassword(memberId);
    }

    @Override
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
        
        //암호화
        PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String encodedPw = pwEncoder.encode(password);
		
        //회원정보 업데이트
        int result = memberDao.updatePassword(memberFindInfo.getMemberId(), encodedPw.toString(), memberFindInfo.getEmail(), memberFindInfo.getPhone());
        if(result == 1) {
        	return password.toString();
        } else {
			return "회원정보가 일치하지 않습니다.";
		}
    }

	@Override
	public boolean checkEmail(String email) {
		return memberDao.checkEmail(email);
	}

	@Override
	public boolean checkMemberId(String memberId) {
		return memberDao.checkMemberId(memberId);
	}

	@Override
    public boolean checkEmail2(String email) {
        return memberDao.checkEmail2(email);
    }

    @Override
    public boolean checkMemberId2(String memberId) {
        return memberDao.checkMemberId2(memberId);
    }

}

