package com.team2.board.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReplyVO {
	int replyId;
	String content;
	Date writeDate;
	int boardId;
	int memberId;
}
