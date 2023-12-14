package com.team2.board.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.team2.board.model.BoardUploadFile;
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
	
	public int maxBoardId();
	


}
