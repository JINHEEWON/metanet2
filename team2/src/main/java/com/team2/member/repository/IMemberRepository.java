package com.team2.member.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.team2.member.model.Member;
import com.team2.member.model.MemberDelete;
import com.team2.member.model.MemberFindInfo;
import com.team2.member.model.Email;
import com.team2.member.model.Login;
import com.team2.member.model.MemberId;

@Repository
@Mapper
public interface IMemberRepository {
   Member selectMember(String memberId);
   Member loginMember(Login login);
   void insertMember(Member member) ;
   void updateMember(Member member);
   boolean checkEmail(@Param("email") String email);
   boolean checkMemberId(@Param("memberId") String memberId);
   boolean checkEmail2(String email);
   boolean checkMemberId2(String memberId);
   int deleteMember(MemberDelete memberDelete);
   String getId(@Param("email") String eamil, @Param("phone") String phone);
   String getPassword(String memberId);
   int updatePassword(@Param("memberId") String memberId, @Param("password") String password, @Param("email") String eamil, @Param("phone") String phone);

}
