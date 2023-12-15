package com.team2.member.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.team2.member.service.IMemberService;

// 사용자 정보를 로드하는 역할 (사용자의 로그인 요청이 있을 때 호출됨)
@Component
public class MemberUserDetailsService implements UserDetailsService {

	@Autowired
	private IMemberService memberService;

	// UserDetailsService 인터페이스의 메서드 구현
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		// 데이터베이스에서 사용자 정보 조회
		Member memberinfo = memberService.selectMember(memberId);

		// 조회한 사용자 정보가 없으면 예외 처리
		if (memberinfo == null) {
			throw new UsernameNotFoundException("[" + memberId + "] 사용자가 없어요");
		}

		// 사용자의 권한 문자열 배열 생성 (데이터베이스에서 조회했다고 가정)
		String[] roles = { "ROLE_USER", "ROLE_ADMIN" };
		
		// 권한 리스트 생성
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);

		// MemberUserDetails 객체를 생성하여 반환
		return new MemberUserDetails(memberinfo.getMemberId(), memberinfo.getPassword(), authorities,
				memberinfo.getEmail());
	}
}
