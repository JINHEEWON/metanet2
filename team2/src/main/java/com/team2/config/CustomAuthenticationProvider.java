package com.team2.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Authentication auth = super.authenticate(authentication);
		if (auth.isAuthenticated()) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			// 여기서 userDetails에서 teamId를 가져와서 권한을 설정
			String teamId = getUserTeamId(userDetails.getUsername()); // 이 메서드를 구현해야 합니다.

			// 권한을 부여하는 부분
			// teamId가 1이면 ROLE_TEAM1, teamId가 2이면 ROLE_TEAM2 등으로 권한을 설정
			auth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
					getAuthorities(teamId));
		}
		return auth;
	}

	private List<GrantedAuthority> getAuthorities(String teamId) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 기본 권한

		if (teamId != null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_TEAM" + teamId)); // 팀별 권한
		}

		return authorities;
	}

	private String getUserTeamId(String username) {
		// 여기에 실제로 데이터베이스에서 teamId를 가져오는 로직을 구현해야 합니다.
		// 예를 들어, Member 테이블에서 username에 해당하는 사용자의 teamId를 조회하는 쿼리 등을 사용할 수 있습니다.
		// 이 예시에서는 가정상의 메서드로 대체되었습니다.
		return "1"; // 가정상의 값
	}
}
