package com.team2.board.model;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString(exclude="file")
public class Board {
	private int boardId;
	private int teamId;
	private String title;
	private String content;
	private Timestamp wirteDate;
	private Timestamp updateDate;
	private int readNum;
	private String memberId;
	private int page;

	private MultipartFile file;
	private int fileId;
	private String fileName;
	private long fileSize;
	private String fileContentType;
}//end class