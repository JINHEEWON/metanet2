package com.team2.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MemberFindInfo {
	private String memberId;
	private String email;
    private String phone;
}
