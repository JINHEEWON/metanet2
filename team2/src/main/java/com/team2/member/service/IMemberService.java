package com.team2.member.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.team2.member.model.Member;
import com.team2.member.model.MemberDelete;
import com.team2.member.model.MemberFindInfo;


public interface IMemberService {
    Member selectMember(String memberId);
    void insertMember(Member member) ;
    void updateMember(Member member);
    int deleteMember(MemberDelete memberDelete);
    String getId(MemberFindInfo memberFindInfo);
    String updatePassword(MemberFindInfo memberFindInfo);
}
