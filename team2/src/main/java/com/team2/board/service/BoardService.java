package com.team2.board.service;


import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.board.model.BoardUploadFile;
import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;
import com.team2.board.repository.IBoardRepository;

@Service
public class BoardService implements IBoardService {
	
	@Autowired
	IBoardRepository boardRepository;

//	@Override
//	public void createBoard(BoardVO board) {
//		System.out.println(board);
//		board.setBoardId(boardRepository.maxBoardId()+1);
		
	public void createBoard(BoardVO board, int maxId) {
		board.setWriteDate(currentTime());
		board.setUpdateDate(currentTime());
		board.setBoardId(maxId+1);
		boardRepository.createBoard(board);
	}
	
	@Override
	public void createBoard(BoardVO board, BoardUploadFile file) {
		System.out.println(board);
		board.setBoardId(boardRepository.maxBoardId()+1);
		boardRepository.createBoard(board);
		if(file != null && file.getFileName() != null && !file.getFileName().equals("")) {
        	file.setBoardId(board.getBoardId());
        	file.setFileId(boardRepository.maxFileId()+1);
        	boardRepository.insertFileData(file);
        }
	}
	
	@Override
	public BoardUploadFile getFile(int fileId) {
		return boardRepository.getFile(fileId);
	}
	

	@Override
	public void updateBoard(BoardVO board) {
		board.setUpdateDate(currentTime());
		boardRepository.updateBoard(board);
	}

	@Override
	public void deleteBoard(int boardId) {
		boardRepository.deleteBoard(boardId);
	}

	@Override
	public void createReply(ReplyVO reply, int maxId) {
		reply.setWriteDate(currentTime());
		reply.setReplyId(maxId+1);
		boardRepository.createReply(reply);
	}

	@Override
	public void updateReply(ReplyVO reply) {
		boardRepository.updateReply(reply);
	}

	@Override
	public void deleteReply(int replyId) {
		boardRepository.deleteReply(replyId);
	}

	@Override
	public List<BoardVO> getBoardList(
		int teamId, 
		int start, 
		int end, 
		String order_by
	) {
		return boardRepository.getBoardList(teamId, start, end, order_by);
	}
	
	@Override
	public List<BoardVO> getBoardListSearch(String title, String content, String memberId, int start, int end,
			String order_by) {
		return boardRepository.getBoardListSearch(title, content, memberId, start, end, order_by);
	}
	
	public int maxBoardId() {
		return boardRepository.maxBoardId();
	}
	
	@Override
	public int maxReplyId() {
		return boardRepository.maxReplyId();
	}
	
	@Override
	public BoardVO getBoardInfo(int boardId) {
		return boardRepository.getBoardInfo(boardId);
	}
	
	@Override
	public void createBoardTeam(int teamId) {
		boardRepository.createBoardTeam(teamId);
	}
	
	private Date currentTime() {
        // 현재 날짜와 시간 얻기
        java.util.Date currentDate = new java.util.Date();
        // java.util.Date를 java.sql.Date로 변환
        Date sqlDate = new Date(currentDate.getTime());
        return sqlDate;
	}
}
