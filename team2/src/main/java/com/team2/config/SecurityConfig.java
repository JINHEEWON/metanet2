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
public class SecurityConfig { // User와 Admin을 구분하는 용도임 
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin((formLogin) -> formLogin
				.loginPage("/member/login")
				.usernameParameter("memberId")
				.defaultSuccessUrl("/member/success"))
			.logout((logout) -> logout
				.logoutUrl("/member/logout")
				.logoutSuccessUrl("/member/login")
				.invalidateHttpSession(true));
		http.authorizeHttpRequests()
			.requestMatchers("/file/**").hasRole("ADMIN")
			.requestMatchers("/**","/css/**","/js/**","/images/**").permitAll()
			.requestMatchers("/member/insert","/member/login").permitAll();
		return http.build();
	}
	
	@Bean
	@ConditionalOnMissingBean(UserDetailsService.class)
	public InMemoryUserDetailsManager userDetailsService() {
		List<UserDetails> userDetailsList = new ArrayList<>();
		userDetailsList.add(User.withUsername("foo")
				.password("{noop}demo")
				.roles("ADMIN").build());
		userDetailsList.add(User.withUsername("bar")
				.password("{noop}demo")
				.roles("USER").build());
		userDetailsList.add(User.withUsername("ted")
				.password("{noop}demo")
				.roles("ADMIN","USER").build());
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(userDetailsList);
		return manager;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}














