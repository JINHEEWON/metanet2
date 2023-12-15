package com.team2.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((csrfConfig) -> csrfConfig.disable());
	      // 로그인 폼에 대한 설정
	      http.formLogin((formLogin) -> formLogin
	            // 로그인 뷰 페이지
	            .loginPage("/login")
	            .usernameParameter("memberId")
	            .defaultSuccessUrl("/"))
	      
	      // 로그아웃
          .logout(logout -> logout
                // 로그아웃 뷰 페이지
                .logoutUrl("/logout")
                // 로그아웃에 성공했을 때 이동할 페이지
                .logoutSuccessUrl("/login")
                // 세션 인증 정보에 대해서 로그아웃시 비활성화
                .invalidateHttpSession(true));
		
	      http.authorizeHttpRequests()
	      	.antMatchers("/checkemail/**", "/checkmemberid/**").permitAll()
	        .requestMatchers("/board/**").hasAnyRole("USER", "ADMIN");
			return http.build();
	   }
	
	
	
	
	
	

	@Bean
	@ConditionalOnMissingBean(UserDetailsService.class)
	public InMemoryUserDetailsManager userDetailsService() {
		List<UserDetails> userDetailsList = new ArrayList<>();
		userDetailsList.add(User.withUsername("foo").password("{noop}demo").roles("ADMIN").build());
		userDetailsList.add(User.withUsername("bar").password("{noop}demo").roles("USER").build());
		userDetailsList.add(User.withUsername("ted").password("{noop}demo").roles("ADMIN", "USER").build());
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(userDetailsList);
		return manager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
