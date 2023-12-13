package com.team2.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.team2.board.model.Board;
import com.team2.board.model.BoardUploadFile;
import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;


@Repository
@Mapper
public interface IBoardRepository {
	
	public BoardVO createBoard(BoardVO board);
	public BoardVO updateBoard(BoardVO board);
	public String deleteBoard(String boardId);
	
	public ReplyVO createReply(ReplyVO reply);
	public ReplyVO updateReply(ReplyVO reply);
	public String deleteReply(String replyId);
	
}
