package com.team2.board.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.board.model.BoardUploadFile;
import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;
import com.team2.board.repository.IBoardRepository;

@Service
public class BoardService implements IBoardService {
	
	@Autowired
	IBoardRepository boardRepository;

	@Override
	public void createBoard(BoardVO board) {
		System.out.println(board);
		board.setBoardId(boardRepository.maxBoardId()+1);
		boardRepository.createBoard(board);
	}
	
	@Override
	public void createBoard(BoardVO board, BoardUploadFile file) {
		System.out.println(board);
		board.setBoardId(boardRepository.maxBoardId()+1);
		boardRepository.createBoard(board);
		if(file != null && file.getFileName() != null && !file.getFileName().equals("")) {
        	file.setBoardId(board.getBoardId());
        	file.setFileId(boardRepository.MaxFileId()+1);
        	boardRepository.insertFileData(file);
        }
	}
	
	@Override
	public BoardUploadFile getFile(int fileId) {
		return boardRepository.getFile(fileId);
	}
	

	@Override
	public void updateBoard(BoardVO board) {
		boardRepository.updateBoard(board);
	}

	@Override
	public void deleteBoard(int boardId) {
		boardRepository.deleteBoard(boardId);
	}

	@Override
	public void createReply(ReplyVO reply) {
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

	








}
