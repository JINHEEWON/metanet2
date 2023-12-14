package com.team2.board.service;


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
	public void createBoard(BoardVO board, int maxId) {
		System.out.println(board);
		board.setBoardId(maxId+1);
		boardRepository.createBoard(board);
	}
	
//	public void createBoard(BoardVO board, int maxId) {
//	    // MyBatis나 JDBC를 사용하여 데이터베이스 연결 및 SQL 실행하는 코드
//	    try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
//	        // MyBatis의 Mapper를 사용하여 SQL 실행
//	        BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
//	        
//	        // board 객체와 maxId 값을 매개변수로 전달하여 SQL 실행
//	        boardMapper.createBoard(board, maxId);
//	        
//	        sqlSession.commit(); // 변경사항 커밋
//	    }
//	}


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

	@Override
	public List<BoardVO> getBoardList(
		int teamId, 
		int start, 
		int end, 
		String order_by
	) {
		return boardRepository.getBoardList(teamId, start, end, order_by);
	}
	public int maxBoardId() {
		return boardRepository.maxBoardId();
	}
	

}
