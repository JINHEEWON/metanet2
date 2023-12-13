package com.team2.board.service;

import java.util.List;

import com.team2.board.model.Board;
import com.team2.board.model.BoardUploadFile;
import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;


public interface IBoardService {
	public BoardVO createBoard(BoardVO board);
	public BoardVO updateBoard(BoardVO board);
	public String deleteBoard(String boardId);
	
	public ReplyVO createReply(ReplyVO reply);
	public ReplyVO updateReply(ReplyVO reply);
	public String deleteReply(String replyId);
}
