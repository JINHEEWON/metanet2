package com.team2.member.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.team2.member.model.Member;

@Repository
@Mapper
public interface IMemberRepository {
   Member selectMember(String memberId);
   void insertMember(Member member) ;
   void updateMember(Member member);
   void deleteMember(Member member);
   boolean checkEmail(@Param("email") String email);
   boolean checkMemberId(@Param("memberId") String memberId);
   boolean checkEmail2(String email);
   boolean checkMemberId2(String memberId);
   String getId(@Param("email") String eamil, @Param("phone") String phone); // 회원가입이 된 상태에서 ID를 찾기 위함 
   String getPassword(@Param("memberId") String memberId, @Param("email") String eamil, @Param("phone") String phone);
   String updatePassword(@Param("memberId") String memberId, @Param("password") String password, @Param("email") String eamil, @Param("phone") String phone);
}
