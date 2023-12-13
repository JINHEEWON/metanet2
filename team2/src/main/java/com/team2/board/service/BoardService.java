package com.team2.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.board.model.Board;
import com.team2.board.model.BoardUploadFile;
import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;
import com.team2.board.repository.IBoardRepository;

@Service
public class BoardService implements IBoardService {
	
	@Autowired
	IBoardRepository boardRepository;

	@Override
	public BoardVO createBoard(BoardVO board) {
		return boardRepository.createBoard(board);
	}

	@Override
	public BoardVO updateBoard(BoardVO board) {
		return boardRepository.updateBoard(board);
	}

	@Override
	public String deleteBoard(String boardId) {
		return boardRepository.deleteBoard(boardId);
	}

	@Override
	public ReplyVO createReply(ReplyVO reply) {
		return boardRepository.createReply(reply);
	}

	@Override
	public ReplyVO updateReply(ReplyVO reply) {
		return boardRepository.updateReply(reply);
	}

	@Override
	public String deleteReply(String replyId) {
		return boardRepository.deleteBoard(replyId);
	}

}