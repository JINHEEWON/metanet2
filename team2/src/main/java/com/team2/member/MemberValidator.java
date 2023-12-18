package com.team2.member;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.team2.member.model.Member;

@Component
public class MemberValidator implements Validator {

	// 해당 Validator가 지원하는 클래스인지 확인
	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.isAssignableFrom(clazz);
	}

	// 실제 유효성 검증 로직을 수행
	@Override
	public void validate(Object target, Errors errors) {
		// 전달받은 객체를 Member 클래스로 형변환
		Member member = (Member) target;

		// 비밀번호와 비밀번호 확인 값을 가져옴
		String pw1 = member.getPassword();
		String pw2 = member.getPassword2();

		// 비밀번호와 비밀번호 확인이 일치하는지 검사
		if (pw1 != null && pw1.equals(pw2)) {
			// 일치하면 유효성 검증 통과
		} else {
			// 일치하지 않으면 에러를 Errors 객체에 추가
			// 에러 코드: "PASSWORD_NOT_EQUALS", 필드: "password2", 에러 메시지: "비밀번호 확인이 다릅니다"
			errors.rejectValue("password2", "PASSWORD_NOT_EQUALS", "비밀번호 확인이 다릅니다");
		}
	}
}
