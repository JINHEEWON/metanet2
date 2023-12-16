package com.team2.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인데 등록
public class SecurityConfig { // User와 Admin을 구분하는 용도임

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin((formLogin) -> formLogin.loginPage("/member/login").usernameParameter("memberId")
				.defaultSuccessUrl("/member/success"))
				.logout((logout) -> logout.logoutUrl("/member/logout").logoutSuccessUrl("/member/login")
						.invalidateHttpSession(true));
		http.authorizeHttpRequests()
				.requestMatchers("/member/update").authenticated() // 이거로 들어오면 인증이 필요
				.requestMatchers("/member/delete").authenticated() // 이거로 들어오면 인증이 필요
				.requestMatchers("/member/find/username").authenticated() // 이거로 들어오면 인증이 필요
				.requestMatchers("/member/find/password").authenticated() // 이거로 들어오면 인증이 필요
				.requestMatchers("/board/**").hasAnyRole("USER", "ADMIN") // 인증뿐만 아니라 권한이 있는 사람만 들어올 수 있음
				.requestMatchers("/board/create/**").authenticated()
				.requestMatchers("/reply/**").authenticated()
				.anyRequest().permitAll(); // 나머지는 전부 인증 없이 접근 가능
		return http.build();
	}

//	@Bean
//	@ConditionalOnMissingBean(UserDetailsService.class)
//	public InMemoryUserDetailsManager userDetailsService() {
//		List<UserDetails> userDetailsList = new ArrayList<>();
//		userDetailsList.add(User.withUsername("foo").password("{noop}demo").roles("ADMIN").build());
//		userDetailsList.add(User.withUsername("bar").password("{noop}demo").roles("USER").build());
//		userDetailsList.add(User.withUsername("ted").password("{noop}demo").roles("ADMIN", "USER").build());
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(userDetailsList);
//		return manager;
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
