package com.team2.board.model;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BoardVO {
	int boardId;
	int teamId;
	String title;
	String content;
	Date writeDate; 
    Date updateDate;
	int readNum;
	String memberId;
	
	private MultipartFile file;
	private int fileId;
	private String fileName;
	private long fileSize;
	private String fileContentType;
}
