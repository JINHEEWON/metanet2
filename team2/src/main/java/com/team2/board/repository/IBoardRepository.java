package com.team2.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;


@Repository
@Mapper
public interface IBoardRepository {
	
	public void createBoard(BoardVO board);
	public void updateBoard(BoardVO board);
	public void deleteBoard(@Param("boardId") int boardId);
	
	public void createReply(ReplyVO reply);
	public void updateReply(ReplyVO reply);
	public void deleteReply(@Param("replyId") int replyId);
	
	public List<BoardVO> getBoardList(
		@Param("teamId") int teamId,
		@Param("start") int start,
		@Param("end") int end,
		@Param("order_by") String order_by
	); 
	public int maxBoardId();
	public int maxReplyId();
	
	public BoardVO getBoardInfo(@Param("boardId") int boardId);
}
