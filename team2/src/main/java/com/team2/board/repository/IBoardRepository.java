package com.team2.board.repository;

import java.util.List;

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
	
	void insertFileData(BoardUploadFile file);
	BoardUploadFile getFile(int fileId);

	public List<BoardVO> getBoardList(
		@Param("teamId") int teamId,
		@Param("start") int start,
		@Param("end") int end,
		@Param("order_by") String order_by
	); 
	
	public List<BoardVO> getBoardListSearch(
		@Param("title") String title,
		@Param("content") String content,
		@Param("memberId") String memberId,
		@Param("start") int start,
		@Param("end") int end,
		@Param("order_by") String order_by
	);
	
	public void createBoardTeam(@Param("teamId") int teamId);
	
	public int maxBoardId();
	public int maxReplyId();
	public int maxFileId();
	
	public BoardVO getBoardInfo(@Param("boardId") int boardId);
	public ReplyVO getReplyInfo(@Param("replyId") int replyId);
}
