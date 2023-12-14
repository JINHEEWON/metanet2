package com.team2.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Member {
    private String memberId;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String birth;
    private int teamId;
}
