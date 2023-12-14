package com.team2.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		boardRepository.createBoard(board);
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
