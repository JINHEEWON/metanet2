package com.team2.member.service;

import com.team2.member.model.Email;
import com.team2.member.model.Member;
import com.team2.member.model.MemberDelete;
import com.team2.member.model.MemberFindInfo;
import com.team2.member.model.MemberId;


public interface IMemberService {
    Member selectMember(String memberId);
    void insertMember(Member member) ;
    void updateMember(Member member);
    int deleteMember(MemberDelete memberDelete);
    boolean checkEmail(Email email);
    boolean checkMemberId(MemberId memberId);
    String getId(MemberFindInfo memberFindInfo);
    String getPassword(String memberId);
    String updatePassword(MemberFindInfo memberFindInfo);
}
