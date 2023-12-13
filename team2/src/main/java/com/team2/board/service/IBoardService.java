package com.team2.board.service;

import java.util.List;

import com.team2.board.model.Board;
import com.team2.board.model.BoardUploadFile;
import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;


public interface IBoardService {
	public void createBoard(BoardVO board);
	public void updateBoard(BoardVO board);
	public void deleteBoard(int boardId);
	
	public void createReply(ReplyVO reply);
	public void updateReply(ReplyVO reply);
	public void deleteReply(int replyId);
}
