package com.team2.board.service;

import java.util.List;

import com.team2.board.model.BoardTeam;


public interface IBoardCategoryService {
	List<BoardTeam> selectAllCategory();
	void insertNewCategory(BoardTeam boardCategory);
	void updateCategory(BoardTeam boardCategory);
	void deleteCategory(int categoryId);
}