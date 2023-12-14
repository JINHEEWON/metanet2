<<<<<<< HEAD
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
=======
//package com.team2.board.repository;
//
//import java.util.List;
//
//import org.apache.ibatis.annotations.Mapper;
//import org.springframework.stereotype.Repository;
//
//import com.team2.board.model.BoardCategory;
//
//
//@Repository
//@Mapper
//public interface IBoardCategoryRepository {
//	int selectMaxCategoryId();
//	List<BoardCategory> selectAllCategory();
//	void insertNewCategory(BoardCategory boardCategory);
//	void updateCategory(BoardCategory boardCategory);
//	void deleteCategory(int categoryId);
//}
>>>>>>> 50d0f222ceac2b709d91c560328b8a4324d5ee22
