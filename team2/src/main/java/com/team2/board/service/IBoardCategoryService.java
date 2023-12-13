package com.team2.board.service;

import java.util.List;

import com.team2.board.model.BoardCategory;


public interface IBoardCategoryService {
	List<BoardCategory> selectAllCategory();
	void insertNewCategory(BoardCategory boardCategory);
	void updateCategory(BoardCategory boardCategory);
	void deleteCategory(int categoryId);
}