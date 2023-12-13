package com.team2.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.board.model.BoardTeam;
import com.team2.board.repository.IBoardCategoryRepository;


@Service
public class BoardCategoryService implements IBoardCategoryService{

	@Autowired
	IBoardCategoryRepository boardCategoryRepository;
	
	@Override
	public List<BoardTeam> selectAllCategory() {
		return boardCategoryRepository.selectAllCategory();
	}

	@Override
	public void insertNewCategory(BoardTeam boardCategory) {
		int newCategoryId = boardCategoryRepository.selectMaxCategoryId() + 1;
		boardCategory.setCategoryId(newCategoryId);
		boardCategoryRepository.insertNewCategory(boardCategory);
	}

	@Override
	public void updateCategory(BoardTeam boardCategory) {
		boardCategoryRepository.updateCategory(boardCategory);
	}

	@Override
	public void deleteCategory(int categoryId) {
		boardCategoryRepository.deleteCategory(categoryId);
	}
}