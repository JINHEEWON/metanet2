package com.team2.board.service;

import com.team2.board.model.BoardUploadFile;
import java.util.List;
import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;


public interface IBoardService {
	public void createBoard(BoardVO board, int id);
	public void createBoard(BoardVO board, BoardUploadFile file);
	
	BoardUploadFile getFile(int fileId);
	
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
	
	public List<BoardVO> getBoardListSearch(
		String title,
		String content,
		String memberId,
		int start,
		int end,
		String order_by
	);
	
	public int maxBoardId();
	public int maxReplyId();
	
	public BoardVO getBoardInfo(int boardId);
	
	public void createBoardTeam(int teamId);
}
