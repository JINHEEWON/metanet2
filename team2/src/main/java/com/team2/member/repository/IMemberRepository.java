package com.team2.member.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.team2.member.model.Member;
import com.team2.member.model.MemberDelete;
import com.team2.member.model.MemberFindInfo;

@Repository
@Mapper
public interface IMemberRepository {
   Member selectMember(String memberId);
   void insertMember(Member member) ;
   void updateMember(Member member);
   int deleteMember(MemberDelete memberDelete);
   String getId(@Param("email") String eamil, @Param("phone") String phone);
   String getPassword(MemberFindInfo memberFindInfo);
   int updatePassword(@Param("memberId") String memberId, @Param("password") String password, @Param("email") String eamil, @Param("phone") String phone);
}
