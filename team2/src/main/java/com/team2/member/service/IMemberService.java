package com.team2.member.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.team2.member.model.Member;

public interface IMemberService {
    Member selectMember(String memberId);
    void insertMember(Member member) ;
    void updateMember(Member member);
    void deleteMember(Member member);
    boolean checkEmail(@Param("email") String email);
    boolean checkMemberId(@Param("memberId") String memberId);
    String getId(@Param("email") String eamil, @Param("phone") String phone);
    String updatePassword(@Param("memberId") String memberId, @Param("password") String password, @Param("email") String eamil, @Param("phone") String phone);
}
