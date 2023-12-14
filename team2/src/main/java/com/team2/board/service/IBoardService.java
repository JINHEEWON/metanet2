package com.team2.board.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.team2.board.model.Board;
import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;


public interface IBoardService {
	public void createBoard(BoardVO board, int maxId);
	public void updateBoard(BoardVO board);
	public void deleteBoard(int boardId);
	
	public void createReply(ReplyVO reply, int maxId);
	public void updateReply(ReplyVO reply);
	public void deleteReply(int replyId);
	
	public List<BoardVO> getBoardList(
		int teamId,
		int page,
		int per_page,
		String order_by
	); 
	public int maxBoardId();
	public int maxReplyId();
}
