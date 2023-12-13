package com.team2.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.team2.board.model.BoardTeam;


@Repository
@Mapper
public interface IBoardCategoryRepository {
	int selectMaxCategoryId();
	List<BoardTeam> selectAllCategory();
	void insertNewCategory(BoardTeam boardCategory);
	void updateCategory(BoardTeam boardCategory);
	void deleteCategory(int categoryId);
}