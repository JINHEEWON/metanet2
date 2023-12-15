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


@Component
public class MemberUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IMemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loaduser");
		Member memberinfo = memberService.selectMember(username);
		System.out.println(username);
		System.out.println(memberinfo);
		if(memberinfo==null) {
			throw new UsernameNotFoundException("["+username+"]사용자가 없어요");
		}
		String[] roles = {"ROLE-USER","ROLE_ADMIN"}; //데이터베이스에서 조회했다고 가정.
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
		
		//return new User(memberinfo.getUserid(),"{noop}"+memberinfo.getPassword(),authorities);
		return new MemberUserDetails(memberinfo.getMemberId(), memberinfo.getPassword(), authorities, memberinfo.getEmail());
	}

}
