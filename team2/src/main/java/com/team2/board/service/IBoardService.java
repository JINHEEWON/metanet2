<<<<<<< HEAD
package com.team2.board.service;

import java.util.List;

import com.team2.board.model.Board;
import com.team2.board.model.BoardUploadFile;


public interface IBoardService {
	void insertArticle(Board boardId);
	void insertArticle(Board boardId, BoardUploadFile file);
	
	List<Board> selectArticleListByCategory(int categoryId, int page);
	
	Board selectArticle(int boardId);
	
	BoardUploadFile getFile(int fileId);
	
	void replyArticle(Board board);
	void replyArticle(Board board, BoardUploadFile file);

	String getPassword(int boardId);
	
	void updateArticle(Board board);
	void updateArticle(Board board, BoardUploadFile file);
	
	Board selectDeleteArticle(int boardId);
	void deleteArticle(int boardId, int replyNumber);
	
	int selectTotalArticleCount();
	int selectTotalArticleCountByteamId(int categoryId);
	
	List<Board> searchListByContentKeyword(String keyword, int page);
	int selectTotalArticleCountByKeyword(String keyword);
}
=======
//package com.team2.board.service;
//
//import java.util.List;
//
//import com.team2.board.model.Board;
//import com.team2.board.model.BoardUploadFile;
//
//
//public interface IBoardService {
//	void insertArticle(Board boardId);
//	void insertArticle(Board boardId, BoardUploadFile file);
//	
//	List<Board> selectArticleListByCategory(int categoryId, int page);
//	
//	Board selectArticle(int boardId);
//	
//	BoardUploadFile getFile(int fileId);
//	
//	void replyArticle(Board board);
//	void replyArticle(Board board, BoardUploadFile file);
//
//	String getPassword(int boardId);
//	
//	void updateArticle(Board board);
//	void updateArticle(Board board, BoardUploadFile file);
//	
//	Board selectDeleteArticle(int boardId);
//	void deleteArticle(int boardId, int replyNumber);
//	
//	int selectTotalArticleCount();
//	int selectTotalArticleCountByCategoryId(int categoryId);
//	
//	List<Board> searchListByContentKeyword(String keyword, int page);
//	int selectTotalArticleCountByKeyword(String keyword);
//}
>>>>>>> 50d0f222ceac2b709d91c560328b8a4324d5ee22
