package com.team2.member.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.team2.member.model.Member;
import com.team2.member.model.Email;
import com.team2.member.model.Member;
import com.team2.member.model.MemberDelete;
import com.team2.member.model.MemberFindInfo;
import com.team2.member.model.MemberId;

public interface IMemberService {
    Member selectMember(String memberId);
    void insertMember(Member member) ;
    void updateMember(Member member);
    boolean checkEmail(@Param("email") String email);
    boolean checkMemberId(@Param("memberId") String memberId);
    boolean checkEmail2(String email);
    boolean checkMemberId2(String memberId);
    int deleteMember(MemberDelete memberDelete);
    String getId(MemberFindInfo memberFindInfo);
    String getPassword(String memberId);
    String updatePassword(MemberFindInfo memberFindInfo);
}
