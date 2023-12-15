package com.team2.member.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

//사용자의 인증 및 권한 정보를 담고 있음
public class MemberUserDetails extends User {

	private static final long serialVersionUID = 2039986090449208134L;
	private String email;

	// MemberUserDetails 객체 생성자
	public MemberUserDetails(String memberId, String password, Collection<? extends GrantedAuthority> authorities,
			String email) {
		// User 클래스의 생성자 호출하여 사용자 아이디, 비밀번호, 권한 정보 초기화
		super(memberId, password, authorities);
		// 사용자의 이메일 정보 초기화
		this.email = email;
	}

	// 사용자의 이메일 정보를 반환하는 메서드
	public String getEmail() {
		return this.email;
	}
}
